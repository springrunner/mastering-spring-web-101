import { LocalStorageTodosService } from '../../application/todo-service.js';
import { Todos, TodosView } from './todos.js';
import TodosController from './controller.js';

const todosView = new TodosView();

const todos = new Todos(LocalStorageTodosService(localStorage));
todos.subscribe(todosView);

const todosControler = new TodosController(todos);
todosControler.bindTodosViewCallbacks(todosView);