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

export { LocalStorageUserProfileService, RandomUserCountService };