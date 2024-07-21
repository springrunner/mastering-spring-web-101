class FeatureToggles {
  constructor(featureTogglesService) {
    this.featureTogglesService = featureTogglesService;
    this.subscribers = [];

    this.props = {
      auth: false,
      onlineUsersCounter: false,
    };

    this.refresh();
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedFeatureToggles(this.props));
  }

  async refresh() {
    this.props = await this.featureTogglesService.fetchAll();
    this.notify();
  }

  isAuth() {
    return this.props.auth;
  }

  isOnlineUsersCounter() {
    return this.props.onlineUsersCounter;
  }
};

export { FeatureToggles };