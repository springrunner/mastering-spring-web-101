export default class TodosController {
  constructor(todos) {
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
};