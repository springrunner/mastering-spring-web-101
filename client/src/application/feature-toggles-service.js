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

const WebAPIFeatureTogglesService = (apiUrl = "/api/feature-toggles") => {
  const headers = { 'Content-Type': 'application/json' };
  const handleResponse = async (response) => {
    if (!response.ok) {
      if (response.status === 404) { // 404 에러 처리
        console.warn('feature-toggles are not yet implemented on the server');
        return {
          auth: false,
          onlineUsersCounter: false
        };
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
    fetchAll: async () => {
      const response = await fetch(apiUrl);
      return await handleResponse(response);
    }
  };  
};

export { QueryStringFeatureTogglesService, WebAPIFeatureTogglesService };