import { Snackbar } from './component.js'

class UserSession {
  constructor(userProfileService) {
    this.userProfileService = userProfileService;
    this.subscribers = [];

    this.props = { 
      userProfile: { name: 'Guest', profilePictureUrl: '/profile-picture.png' }
    };

    this.refresh();
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedUserSession(this.props));
  }

  async refresh() {
    this.props = {
      userProfile: await this.userProfileService.get()
    }
    this.notify();
  }

  async logout() {
    this.props = {
      userProfile: await this.userProfileService.clear()
    }
    this.notify();
  }
};

class UserSessionView {
  constructor() {
    this.snackbar = Snackbar();

    this.userSessionContainer = document.querySelector('.user-session-container');
    this.loginGuide = document.querySelector('.login-guide');
    this.loginLink = document.querySelector('.login-guide a');
    this.userProfile = document.querySelector('.user-profile');
    this.username = document.querySelector('.user-profile strong');
    this.userProfilePicture = document.querySelector('.user-profile img');    
    this.logoutLink = document.querySelector('.logout-link');

    this.onLogin = null;
    this.onLogout = null;

    this.attachEventListeners();
  }

  attachEventListeners() {
    this.loginLink.addEventListener('click', event => {
      if (!this.onLogin) {
        console.warn('Warning: onLogin handler is not defined');
        return;
      }

      this.onLogin();
    });

    this.logoutLink.addEventListener('click', event => {
      if (!this.onLogout) {
        console.warn('Warning: onLogout handler is not defined');
        return;
      }

      this.onLogout();
    });
  }

  onChangedUserSession(userSession) {
    this.userSessionContainer.style.display = 'block';

    const { userProfile } = userSession || {};
    if (userProfile) {
      this.loginGuide.style.display = 'none';
      this.userProfile.style.display = 'block';
      this.username.textContent = userProfile.name ?? 'Guest';
      this.userProfilePicture.src = userProfile.profilePictureUrl ?? '';
    } else {
      this.loginGuide.style.display = 'block';
      this.userProfile.style.display = 'none';
    }  
  }

  onError(error) {
    this.snackbar.show(error);
  }
}

export { UserSession, UserSessionView };