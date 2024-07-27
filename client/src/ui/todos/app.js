import { LocalStorageUserProfileService, RandomUserCountService } from '../../application/user-service.js';
import { LocalStorageTodosService, WebAPITodosService } from '../../application/todo-service.js';
import { UserSession, UserSessionView  } from './user-session.js';
import { UserCount, UserCountView } from './user-count.js';
import { Todos, TodosView } from './todos.js';
import TodosController from './controller.js';

import { FeatureToggles } from '../feature-toggles.js';
import { QueryStringFeatureTogglesService } from '../../application/feature-toggles-service.js';

const isDevelopmentMode = import.meta.env.MODE === 'development'

const userSessionView = new UserSessionView();
const userCountView = new UserCountView();
const todosView = new TodosView();

const userSession = new UserSession(LocalStorageUserProfileService(localStorage));
userSession.subscribe(userSessionView);

const userCount = new UserCount(RandomUserCountService());
userCount.subscribe(userCountView);

const todos = new Todos(isDevelopmentMode ? LocalStorageTodosService(localStorage) : WebAPITodosService());
todos.subscribe(todosView);

const featureToggles = new FeatureToggles(QueryStringFeatureTogglesService());
featureToggles.subscribe(userSessionView);
featureToggles.subscribe(userCountView);
featureToggles.notify();

const todosControler = new TodosController(
  {
    downloadUrl: isDevelopmentMode ? null : '/todos',
    loginUrl: isDevelopmentMode ? '/pages/login.html' : '/login',
    logoutUrl: isDevelopmentMode ? null : '/logout',
    logoutSuccessUrl: isDevelopmentMode ? '/pages/login.html' : null,
  }, userSession, todos);
todosControler.bindTodosViewCallbacks(todosView);
todosControler.bindUserSessionViewCallbacks(userSessionView);