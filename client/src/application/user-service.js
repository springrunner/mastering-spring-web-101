const LocalStorageUserProfileService = (localStorage) => {
  const key = 'user-profile';

  return {
    set: async (userProfile) => localStorage.setItem(key, JSON.stringify(userProfile)),
    get: async () => JSON.parse(localStorage.getItem(key)) || null,
    clear: async () => localStorage.removeItem(key)
  };
}

export { LocalStorageUserProfileService };