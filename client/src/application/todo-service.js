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
  const handleResponse = async (response) => {
    if (response.status >= 200 && response.status < 300) {
      return response.json().catch(parseError => null);
    } else {
      let data = {};
      try {
        data = await response.json();
      } catch (parseError) {
        console.warn('Failed to parse JSON response:', parseError);
      }

      const error = new Error(data.message ?? "Failed to process in server");
      error.name = data.error ?? "Unknown Error";
      error.details = (data.errors ?? []).map(it => typeof it === 'string' ? it : it.defaultMessage)

      throw error;
    }
  }

  const service = {
    all: async () => {
      const response = await fetch(apiUrl);
      return await handleResponse(response);
    },
    add: async (todo) => {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers,
        body: JSON.stringify(todo)
      });
      return await handleResponse(response);
    },
    edit: async (updatedTodo) => {
      const response = await fetch(`${apiUrl}/${updatedTodo.id}`, {
        method: 'PUT',
        headers,
        body: JSON.stringify(updatedTodo)
      });
      return await handleResponse(response);
    },
    remove: async (todoId) => {
      const response = await fetch(`${apiUrl}/${todoId}`, {
        method: 'DELETE',
        headers
      });
      return await handleResponse(response);
    },
    clearCompleted: async () => {
      const todos = await service.all();
      const completedTodos = todos.filter(todo => todo.completed);
      await Promise.all(completedTodos.map(todo => service.remove(todo.id)));
    }
  };

  return service;
};

export { LocalStorageTodosService, WebAPITodosService };