import { describe, test, expect, beforeEach } from 'vitest';
import { UserSessionView } from '../../../src/ui/todos/user-session.js';

describe('UserSessionView', () => {
  beforeEach(() => {
    document.body.innerHTML = `
      <style>
        .login-guide { display: none; }
        .user-profile { display: none; }
      </style>
      <div class="user-session-container">
        <p class="login-guide"><a href="#/login">login</a></p>
        <div class="user-profile">
          <img src="/profile-picture.png" />
          <p>Welcome your visit to <strong>Guest</strong></p>
          <div class="settings">
            <button type="button">&#9776;</button>
            <div class="buttons">
              <a href="#/update-profile-picture" class="update-profile-picture-link">Update Profile Picture</a>
              <a href="#/logout" class="logout-link">Logout</a>
            </div>
          </div>
        </div>
      </div>
    `;
  });

  test('초기 상태에서 둘 다 숨겨져야 함', () => {
    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    const loginGuideDisplay = window.getComputedStyle(loginGuide).display;
    const userProfileDisplay = window.getComputedStyle(userProfile).display;

    expect(loginGuideDisplay).toBe('none');
    expect(userProfileDisplay).toBe('none');
  });

  test('로그인 상태일 때 userProfile만 표시', () => {
    const view = new UserSessionView();

    view.onChangedUserSession({
      userProfile: { name: 'John', profilePictureUrl: '/pic.png' }
    });

    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    expect(loginGuide.style.display).toBe('none');
    expect(userProfile.style.display).toBe('block');
  });

  test('비로그인 상태일 때 loginGuide만 표시', () => {
    const view = new UserSessionView();

    view.onChangedUserSession({ userProfile: null });

    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    expect(loginGuide.style.display).toBe('block');
    expect(userProfile.style.display).toBe('none');
  });

  test('userProfile 객체가 없으면 loginGuide 표시', () => {
    const view = new UserSessionView();

    view.onChangedUserSession({});

    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    expect(loginGuide.style.display).toBe('block');
    expect(userProfile.style.display).toBe('none');
  });

  test('Race condition: 두 요소가 동시에 표시되지 않아야 함', () => {
    const view = new UserSessionView();

    // Feature toggles와 user session이 다른 순서로 도착하는 시나리오
    view.onChangedFeatureToggles({ auth: true });
    view.onChangedUserSession({
      userProfile: { name: 'John', profilePictureUrl: '/pic.png' }
    });

    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    const loginGuideVisible = loginGuide.style.display !== 'none';
    const userProfileVisible = userProfile.style.display !== 'none';

    // 둘 다 동시에 보이면 안 됨
    const bothVisible = loginGuideVisible && userProfileVisible;
    expect(bothVisible).toBe(false);

    // 둘 중 하나는 반드시 보여야 함
    const oneVisible = loginGuideVisible || userProfileVisible;
    expect(oneVisible).toBe(true);
  });

  test('역순 Race condition: user session이 먼저 도착하는 경우', () => {
    const view = new UserSessionView();

    // User session이 먼저, feature toggles가 나중
    view.onChangedUserSession({
      userProfile: { name: 'John', profilePictureUrl: '/pic.png' }
    });
    view.onChangedFeatureToggles({ auth: true });

    const loginGuide = document.querySelector('.login-guide');
    const userProfile = document.querySelector('.user-profile');

    const loginGuideVisible = loginGuide.style.display !== 'none';
    const userProfileVisible = userProfile.style.display !== 'none';

    const bothVisible = loginGuideVisible && userProfileVisible;
    expect(bothVisible).toBe(false);
  });

  test('userProfile의 name과 profilePictureUrl이 올바르게 표시됨', () => {
    const view = new UserSessionView();

    view.onChangedUserSession({
      userProfile: {
        name: 'Alice',
        profilePictureUrl: '/alice.png'
      }
    });

    const username = document.querySelector('.user-profile strong');
    const profilePicture = document.querySelector('.user-profile img');

    expect(username.textContent).toBe('Alice');
    expect(profilePicture.src).toContain('/alice.png');
  });

  test('userProfile.name이 없으면 Guest로 표시', () => {
    const view = new UserSessionView();

    view.onChangedUserSession({
      userProfile: {
        profilePictureUrl: '/pic.png'
      }
    });

    const username = document.querySelector('.user-profile strong');
    expect(username.textContent).toBe('Guest');
  });
});
