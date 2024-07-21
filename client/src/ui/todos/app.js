import { LocalStorageUserProfileService } from '../../application/user-service.js';
import { LocalStorageTodosService } from '../../application/todo-service.js';
import { UserSession, UserSessionView  } from './user-session.js'; 
import { Todos, TodosView } from './todos.js';
import TodosController from './controller.js';

const userSessionView = new UserSessionView();
const todosView = new TodosView();

const userSession = new UserSession(LocalStorageUserProfileService(localStorage));
userSession.subscribe(userSessionView);

const todos = new Todos(LocalStorageTodosService(localStorage));
todos.subscribe(todosView);

const todosControler = new TodosController(null, userSession, todos);
todosControler.bindTodosViewCallbacks(todosView);
todosControler.bindUserSessionViewCallbacks(userSessionView);