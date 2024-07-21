class UserCount {
  constructor(userCountService) {
    this.userCountService = userCountService;    
    this.subscribers = [];
    
    this.userCount = 1;

    this.userCountService.connect(userCount => {
      this.userCount = userCount;
      this.notify();
    });
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedUserCount(this.userCount));
  }
};

class UserCountView {
  constructor() {
    this.userCountContainer = document.querySelector('.user-count');
    this.userCount = document.querySelector('.user-count strong');
  }

  onChangedFeatureToggles(featureToggles) {
    this.userCountContainer.style.display = featureToggles.onlineUsersCounter ? 'block' : 'none';
  }

  onChangedUserCount(count) {
    this.userCount.textContent = count;
  }
}

export { UserCount, UserCountView };