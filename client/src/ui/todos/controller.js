export default class TodosController {
  constructor(props, userSession, todos) {
    this.props = props || {};
    this.userSession = userSession;
    this.todos = todos;
  }

  bindTodosViewCallbacks(todosView) {
    const onChangedTodos = todosView.onChangedTodos.bind(todosView);
    const onError = todosView.onError.bind(todosView);
    
    todosView.onToggleAll = (completed) => {
      this.todos.toggleAll(completed).catch(onError)
    };

    todosView.onCreateTodo = (text) => {
      this.todos.add(text).catch(onError);
    };

    todosView.onUpdateTodo = (todoId, text, completed) => {
      if (todoId == null || todoId.trim() === '') {
        console.error('Update todo: todoId cannot be null, undefined, or empty');
        throw new Error('Please enter an ID to update a todo');
      }

      this.todos.edit(todoId, text, completed).catch(onError);
    };
    
    todosView.onDeleteTodo = (todoId) => {
      if (todoId == null || todoId.trim() === '') {
        console.error('Delete todo: todoId cannot be null, undefined, or empty');
        throw new Error('Please enter an ID to delete a todo');
      }

      this.todos.remove(todoId).catch(onError);
    };

    todosView.onFilterTodos = (filter) => {
      this.todos.refresh(filter).catch(onError);
    }

    todosView.onClearCompletedTodos = () => {
      this.todos.clearCompleted().catch(onError);
    };

    todosView.onDownloadTodos = (outputStream) => {
      if (this.props.downloadUrl) {
        outputStream(this.props.downloadUrl);
        return;
      }

      this.todos.all().then(todos => {
        const headers = 'id,text,completed,createdAt\n';
        const rows = todos.map(todo => {
          const createdAt = new Date(todo.createdAt).toString();
          return `${todo.id},"${todo.text}",${todo.completed},${createdAt}`
        }).join('\n');
                
        outputStream(headers + rows, 'text/csv;charset=utf-8;', 'todos.csv');
      });
    };
    
    this.todos.refresh().catch(error => {
      onChangedTodos([]);
      onError(error);
    });
  }

  bindUserSessionViewCallbacks(userSessionView) {
    const onError = userSessionView.onError.bind(userSessionView);

    userSessionView.onLogin = () => {
      if (!this.props.loginUrl) {
        userSessionView.onError('Login is not supported');
        console.warn('Warning: login-url is not defined');
        return;
      }

      document.location.href = this.props.loginUrl;
    };

    userSessionView.onUpdateProfilePicture = (profilePicture) => {
      this.userSession.updateProfilePicture(profilePicture).catch(onError);
    };

    userSessionView.onLogout = () => {
      if (this.props.logoutUrl) {
        document.location.href = this.props.logoutUrl;
      } else {
        this.userSession.logout().then(() => {
          if (!this.props.logoutSuccessUrl) {          
            console.warn('Warning: logout-success-url is not defined');
            return;
          }
          
          document.location.href = this.props.logoutSuccessUrl;
        });
      }
    };
  }
};