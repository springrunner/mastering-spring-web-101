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

export { LocalStorageUserProfileService, RandomUserCountService, OnlineUserCountService };