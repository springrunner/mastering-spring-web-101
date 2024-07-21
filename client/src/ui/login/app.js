import { LocalStorageUserProfileService } from '../../application/user-service.js';

import 'todomvc-app-css/index.css';
import 'todomvc-common/base.css';
import './app.css';

/**
 * In the development environment, this simulate the login process.
 * In production environments, this should be handled on the server via a login form request.
 */
const mode = import.meta.env.MODE
if (mode === 'development') {
  console.info('Running in development mode');
  
  const userProfileService = LocalStorageUserProfileService(localStorage);
  const form = document.getElementsByClassName('login-form')[0];
  const username = document.getElementById('username');

  form.addEventListener('submit', function(event) {
    event.preventDefault();    
    
    const userProfile = { name: username.value, profilePictureUrl: '/profile-picture.png' };      
    userProfileService.set(userProfile);

    window.location.href = '/pages/todos.html';
  });
}