const LocalStorageUserProfileService = (localStorage) => {
  const key = 'user-profile';

  return {
    set: async (userProfile) => localStorage.setItem(key, JSON.stringify(userProfile)),
    get: async () => JSON.parse(localStorage.getItem(key)) || null,
    clear: async () => localStorage.removeItem(key),
    updateProfilePicture: async(profilePicture) => {
      const userProfile = JSON.parse(localStorage.getItem(key)) || null;
      if (userProfile === null) {
        throw new Error('user-profile is null');
      }
      
      userProfile.profilePictureUrl = await new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = event => resolve(event.target.result);
        reader.onerror = error => reject(error);
        reader.readAsDataURL(profilePicture);
      });
      localStorage.setItem(key, JSON.stringify(userProfile));
      
      return userProfile;
    }    
  };
}

const WebAPIUserProfileService = (
  fetchProfileUrl = "/api/user/profile", 
  clearProfileUrl = "/logout",
  updateProfilePictureUrl = "/api/user/profile-picture",
) => {
  const headers = { 'Content-Type': 'application/json' };
  const handleResponse = async (response) => {
    if (!response.ok) {
      if (response.status === 401) {
        console.warn('Unauthorized access detected on the server');
        return null;
      } else if (response.status === 404) {
        console.warn('user-profile are not yet implemented on the server');
        return null;
      }

      const data = await response.json();
      const error = new Error(data.message ?? "Failed to process in server");
      error.name = data.error ?? "Unknown Error";
      error.details = (data.errors ?? []).map(it => typeof it === 'string' ? it : it.defaultMessage)

      throw error;
    }
    return response.json();
  }

  return {
    set: (userProfile) => {
      throw new Error("Unsupported set user-profile operation");
    },
    get: async () => {
      const response = await fetch(fetchProfileUrl, {
        headers
      });
      return await handleResponse(response);
    },
    clear: async () => {
      const response = await fetch(clearProfileUrl, {
        headers
      });
      return await handleResponse(response);
    },
    updateProfilePicture: async(profilePicture) => {
      const formData = new FormData();
      formData.append('profilePicture', profilePicture);
    
      const response = await fetch(updateProfilePictureUrl, {
        method: 'POST',
        header: {
          'Content-Type': 'multipart/form-data'
        },
        body: formData
      });
      return await handleResponse(response);
    }    
  };
}

const RandomUserCountService = (interval = 30000) => {
  let intervalId;

  return {
    connect(onUserCountChange) {    
      intervalId = setInterval(() => {
        const number = Math.floor(Math.random() * 10) + 1;
        onUserCountChange(number);
      }, interval);      
    },
    disconnect() {
      clearInterval(intervalId);
    }
  }
}

const OnlineUserCountService = (streamUrl = '/stream/online-users-counter') => {
  let eventSource;
  let isConnected = false;

  return {
    connect(onUserCountChange) {
      if (isConnected) {
        console.warn('Already connected to the event source.');
        return;
      }

      eventSource = new EventSource(streamUrl);
      eventSource.onerror = () => {};
      eventSource.addEventListener('message', (event) => {        
        onUserCountChange(parseInt(event.data, 10));
      });

      isConnected = true;
    },
    disconnect() {
      if (eventSource) {
        eventSource.close();        
        eventSource = null;
        isConnected = false;
      }
    }
  }
}

export { LocalStorageUserProfileService, WebAPIUserProfileService, RandomUserCountService, OnlineUserCountService };