import { Snackbar } from './component.js'

const ENTER_KEY = 'Enter';
const ESCAPE_KEY = 'Escape';

class Todos {
  constructor(todoService) {    
    this.todoService = todoService;
    this.subscribers = [];

    this.data = [];
    this.filter = 'all';
  }

  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }

  notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedTodos(this.data));
  }

  async refresh(filter = 'all') {
    const todos = await this.todoService.all();
    
    this.data = todos.filter(todo =>
      filter === 'all' || 
      (filter === 'active' && !todo.completed) || 
      (filter === 'completed' && todo.completed)
    );
    this.filter = filter;

    this.notify();
  }

  async toggleAll(completed) { 
    const todos = await this.todoService.all();
    const tasks = todos.map(todo => this.todoService.edit({ ...todo, completed }));
    
    return Promise.all(tasks).then(() => this.refresh(this.filter));
  }
  
  add(text) {
    const todo = { id: Date.now().toString(), text, completed: false, createdAt: Date.now(), updatedAt: null };

    return this.todoService.add(todo).then(() => this.refresh(this.filter));
  }

  edit(todoId, text, completed) {
    const updatedTodo = { id: todoId, text, completed, updatedAt: Date.now() };

    return this.todoService.edit(updatedTodo).then(() => this.refresh(this.filter));
  }

  remove(todoId) { 
    return this.todoService.remove(todoId).then(() => this.refresh(this.filter));
  }

  clearCompleted() { 
    return this.todoService.clearCompleted().then(() => this.refresh(this.filter));
  }

  all() {
    return this.todoService.all();
  }
};

class TodosView {
  constructor() {
    this.snackbar = Snackbar();
    
    this.newTodoInput = document.querySelector('.new-todo');
    this.toggleAllCheckbox = document.querySelector('.toggle-all');
    this.toggleAll = document.querySelector('.toggle-all-label');
    this.todoList = document.querySelector('.todo-list');  
    this.todoCount = document.querySelector('.todo-count strong');
    this.filters = document.querySelector('.filters');
    this.clearCompletedButton = document.querySelector('.clear-completed');
    this.downloadTodosButton = document.querySelector('.download-todos');

    this.onToggleAll = null;
    this.onCreateTodo = null;
    this.onUpdateTodo = null;
    this.onDeleteTodo = null;
    this.onFilterTodos = null;
    this.onClearCompletedTodos = null;
    this.onDownloadTodos = null;

    this.attachEventListeners();
  }

  attachEventListeners() {
    this.toggleAll.addEventListener('click', event => {
      if (!this.onToggleAll) {
        console.warn('Warning: onToggleAll handler is not defined');
        return;
      }

      this.toggleAllCheckbox.checked = !this.toggleAllCheckbox.checked;
      this.onToggleAll(this.toggleAllCheckbox.checked);
    });

    this.newTodoInput.addEventListener('keypress', event => {
      if (!this.onCreateTodo) {
        console.warn('Warning: onCreateTodo handler is not defined');
        return;
      }

      if (event.key === ENTER_KEY && this.newTodoInput.value.trim() !== '') {
        this.onCreateTodo(this.newTodoInput.value.trim());
        this.newTodoInput.value = '';
      }
    });

    this.todoList.addEventListener('click', event => {
      if (!this.onDeleteTodo) {
        console.warn('Warning: onDeleteTodo handler is not defined');
        return;
      }
      if (!this.onUpdateTodo) {
        console.warn('Warning: onUpdateTodo handler is not defined');
        return;
      }

      const target = event.target;
      const todoItem = target.closest('li');
      if (!todoItem) return;

      const id = todoItem.dataset.id;
      if (target.classList.contains('destroy')) {
        this.onDeleteTodo(id);
      } else if (target.classList.contains('toggle')) {
        const label = todoItem.querySelector('label');
        const checkbox = todoItem.querySelector('.toggle');        
        this.onUpdateTodo(id, label.textContent, checkbox.checked);
      }
    });
    this.todoList.addEventListener('dblclick', event => {
      if (!this.onUpdateTodo) {
        console.warn('Warning: onUpdateTodo handler is not defined');
        return;
      }

      const target = event.target;
      const todoItem = target.closest('li');
      if (!todoItem) return;

      if (target.tagName === 'LABEL') {
        const id = todoItem.dataset.id;
        const label = todoItem.querySelector('label');
        const checkbox = todoItem.querySelector('.toggle');        
        const className = todoItem.className;

        const input = document.createElement('input');
        input.className = 'edit';
        input.value = label.textContent;

        todoItem.className = `${className} editing`;
        todoItem.appendChild(input);
        input.focus();      

        input.addEventListener('blur', () => {
          todoItem.className = className;
          todoItem.removeChild(input);
        });   
        input.addEventListener('keyup', event => {
          if (event.key === ESCAPE_KEY) {
            todoItem.className = className;
            todoItem.removeChild(input);
          }         
        });
        input.addEventListener('keypress', event => {
          if (event.key === ENTER_KEY) {
            this.onUpdateTodo(id, input.value, checkbox.checked);
          }         
        });     
      }
    });

    this.filters.addEventListener('click', (event) => {
      if (event.target.tagName === 'A') {
        const selectedFilter = event.target.getAttribute('href').slice(2);
        const filters = document.querySelectorAll('.filters li a');
        filters.forEach(filter => {
          if (filter.getAttribute('href') === `#/${selectedFilter}`) {
            filter.classList.add('selected');
            this.filter = filter.textContent.toLowerCase();
          } else {
            filter.classList.remove('selected');
          }
        });
        this.onFilterTodos(this.filter);
      }
    });

    this.clearCompletedButton.addEventListener('click', (event) => {
      if (!this.onClearCompletedTodos) {
        console.warn('Warning: onClearCompletedTodos handler is not defined');
        return;
      }

      this.onClearCompletedTodos();
    });    

    this.downloadTodosButton.addEventListener('click', (event) => {
      if (!this.onDownloadTodos) {
        console.warn('Warning: onDownloadTodos handler is not defined');
        return;
      }

      const save = (blob, fileName) => {
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.setAttribute('href', url);
        link.setAttribute('download', fileName);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }

      this.onDownloadTodos((...args) => {
        if (args.length === 3) {
          const [content, contentType, fileName] = args;
          save(new Blob([content], { type: contentType }), fileName);
        } else if (args.length === 1) {
          const [url] = args;
          if (typeof url === 'string' && url.startsWith('http')) {
            console.error('Warning: Invalid download url');
            return;
          }

          fetch(url, { headers: { 'Accept': 'text/csv' }}).then(response => {
            let fileName = 'todos.csv';

            const contentDisposition = response.headers.get('Content-Disposition');            
            if (contentDisposition) {
              const matches = contentDisposition.match(/filename\*?=['"]?(?:UTF-\d['"]*)?([^;\r\n"']*)['"]?/i);
              if (matches && matches[1]) {
                fileName = matches[1];
              }
            }

            return response.blob().then(blob => ({ blob, fileName }));
          }).then(({ blob, fileName }) => save(blob, fileName)).catch(this.onError.bind(this));
        } else {
          console.error('Warning: Unable to resolve download todos');
        }
      });
    });    
  }

  onChangedTodos(todos) {
    const completedCount = todos.filter(todo => todo.completed).length;

    this.todoList.innerHTML = todos.map(todo => 
      `<li data-id="${todo.id}" class="${todo.completed ? 'completed' : ''}">
        <div class="view">
          <input class="toggle" type="checkbox" ${todo.completed ? 'checked' : ''}>
          <label>${todo.text}</label>
          <button class="destroy"></button>
        </div>
      </li>`
    ).join('');
    this.todoCount.textContent = todos.length;
    this.clearCompletedButton.style.display = completedCount > 0 ? 'block' : 'none';
  }

  onError(error) {
    this.snackbar.show(error);
  }
};

export { Todos, TodosView };