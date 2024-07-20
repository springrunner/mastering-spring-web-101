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

export { LocalStorageTodosService };