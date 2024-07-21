const QueryStringFeatureTogglesService = () => {
  return {
    fetchAll: async () => {
      const params = new URLSearchParams(window.location.search);
      return {
        auth: params.get('auth') === 'true',
        onlineUsersCounter: params.get('onlineUsersCounter') === 'true'
      };      
    }
  };
}

export { QueryStringFeatureTogglesService };