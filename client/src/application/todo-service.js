const LocalStorageTodosService = (localStorage) => {
  const key = 'todos';
  
  return {
    all: async () => JSON.parse(localStorage.getItem(key)) || [],
    add: async (todo) => {
      const todos = JSON.parse(localStorage.getItem(key)) || [];
      todos.push(todo);
      localStorage.setItem(key, JSON.stringify(todos));
    },
    edit: async (updatedTodo) => {
      let todos = JSON.parse(localStorage.getItem(key)) || [];
      todos = todos.map(todo => todo.id === updatedTodo.id ? {...todo, ...updatedTodo} : todo);
      localStorage.setItem(key, JSON.stringify(todos));
    },
    remove: async (todoId) => {
      const todos = JSON.parse(localStorage.getItem(key)) || [];
      const filteredTodos = todos.filter(todo => todo.id !== todoId);
      localStorage.setItem(key, JSON.stringify(filteredTodos));
    },
    clearCompleted: async () => {
      const todos = JSON.parse(localStorage.getItem(key)) || [];    
      const filteredTodos = todos.filter(todo => !todo.completed);
      localStorage.setItem(key, JSON.stringify(filteredTodos));
    }
  };
}

const WebAPITodosService = (apiUrl = "/api/todos") => {
  const headers = { 'Content-Type': 'application/json' };

  return {
    all: async () => {
      const response = await fetch(apiUrl);
      if (!response.ok) throw new Error(`Failed to fetch all todos: ${response.status}`);
      return await response.json();
    },
    add: async (todo) => {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers,
        body: JSON.stringify(todo)
      });
      if (!response.ok) throw new Error(`Failed to add todo: ${response.status}`);
    },
    edit: async (updatedTodo) => {
      const response = await fetch(`${apiUrl}/${updatedTodo.id}`, {
        method: 'PUT',
        headers,
        body: JSON.stringify(updatedTodo)
      });
      if (!response.ok) throw new Error(`Failed to edit todo: ${response.status}`);
    },
    remove: async (todoId) => {
      const response = await fetch(`${apiUrl}/${todoId}`, {
        method: 'DELETE',
        headers
      });
      if (!response.ok) throw new Error(`Failed to delete todo: ${response.status}`);
    },
    clearCompleted: async () => {
      const todos = await this.all();
      const completedTodos = todos.filter(todo => todo.completed);
      await Promise.all(completedTodos.map(todo => this.remove(todo.id)));
    }
  };
};

export { LocalStorageTodosService, WebAPITodosService };