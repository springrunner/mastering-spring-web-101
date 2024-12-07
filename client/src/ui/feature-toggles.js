class FeatureToggles {
  constructor(featureTogglesService) {
    this.featureTogglesService = featureTogglesService;
    this.subscribers = [];

    this.props = {
      auth: false,
      onlineUsersCounter: false,
    };

    (async () => {
      try {
        this.props = await this.featureTogglesService.fetchAll();
        this.notify();
      } catch (error) {
        console.warn('Failed to fetch feature-toggles:', error);
      }
    })();
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedFeatureToggles(this.props));
  }

  isAuth() {
    return this.props.auth;
  }

  isOnlineUsersCounter() {
    return this.props.onlineUsersCounter;
  }
};

export { FeatureToggles };