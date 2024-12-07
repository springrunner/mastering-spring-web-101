class UserCount {
  constructor(userCountService) {
    this.userCountService = userCountService;    
    this.subscribers = [];
    
    this.userCount = 1;
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedUserCount(this.userCount));
  }

  onChangedFeatureToggles(featureToggles) {
    if (featureToggles.onlineUsersCounter) {
      this.userCountService.connect(userCount => {
        this.userCount = userCount;
        this.notify();
      });
    } else {
      this.userCountService.disconnect();
    }
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