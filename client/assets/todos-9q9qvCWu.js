import "./modulepreload-polyfill-DaKOjhqt.js";
const WebAPIUserProfileService = (fetchProfileUrl = "/api/user/profile", clearProfileUrl = "/logout", updateProfilePictureUrl = "/api/user/profile-picture") => {
  const headers = { "Content-Type": "application/json" };
  const handleResponse = async (response) => {
    if (!response.ok) {
      if (response.status === 401) {
        console.warn("Unauthorized access detected on the server");
        return null;
      } else if (response.status === 404) {
        console.warn("user-profile are not yet implemented on the server");
        return null;
      }
      const data = await response.json();
      const error = new Error(data.message ?? "Failed to process in server");
      error.name = data.error ?? "Unknown Error";
      error.details = (data.errors ?? []).map((it) => typeof it === "string" ? it : it.defaultMessage);
      throw error;
    }
    return response.json();
  };
  return {
    set: (userProfile) => {
      throw new Error("Unsupported set user-profile operation");
    },
    get: async () => {
      const response = await fetch(fetchProfileUrl, {
        headers
      });
      return await handleResponse(response);
    },
    clear: async () => {
      const response = await fetch(clearProfileUrl, {
        headers
      });
      return await handleResponse(response);
    },
    updateProfilePicture: async (profilePicture) => {
      const formData = new FormData();
      formData.append("profilePicture", profilePicture);
      const response = await fetch(updateProfilePictureUrl, {
        method: "POST",
        header: {
          "Content-Type": "multipart/form-data"
        },
        body: formData
      });
      return await handleResponse(response);
    }
  };
};
const OnlineUserCountService = (streamUrl = "/stream/online-users-counter") => {
  let eventSource;
  let isConnected = false;
  return {
    connect(onUserCountChange) {
      if (isConnected) {
        console.warn("Already connected to the event source.");
        return;
      }
      eventSource = new EventSource(streamUrl);
      eventSource.onerror = () => {
      };
      eventSource.addEventListener("message", (event) => {
        onUserCountChange(parseInt(event.data, 10));
      });
      isConnected = true;
    },
    disconnect() {
      if (eventSource) {
        eventSource.close();
        eventSource = null;
        isConnected = false;
      }
    }
  };
};
const WebAPITodosService = (apiUrl = "/api/todos") => {
  const headers = { "Content-Type": "application/json" };
  const handleResponse = async (response) => {
    if (response.status >= 200 && response.status < 300) {
      return response.json().catch((parseError) => null);
    } else {
      let data = {};
      try {
        data = await response.json();
      } catch (parseError) {
        console.warn("Failed to parse JSON response:", parseError);
      }
      const error = new Error(data.message ?? "Failed to process in server");
      error.name = data.error ?? "Unknown Error";
      error.details = (data.errors ?? []).map((it) => typeof it === "string" ? it : it.defaultMessage);
      throw error;
    }
  };
  return {
    all: async () => {
      const response = await fetch(apiUrl);
      return await handleResponse(response);
    },
    add: async (todo) => {
      const response = await fetch(apiUrl, {
        method: "POST",
        headers,
        body: JSON.stringify(todo)
      });
      return await handleResponse(response);
    },
    edit: async (updatedTodo) => {
      const response = await fetch(`${apiUrl}/${updatedTodo.id}`, {
        method: "PUT",
        headers,
        body: JSON.stringify(updatedTodo)
      });
      return await handleResponse(response);
    },
    remove: async (todoId) => {
      const response = await fetch(`${apiUrl}/${todoId}`, {
        method: "DELETE",
        headers
      });
      return await handleResponse(response);
    },
    clearCompleted: async () => {
      const todos2 = await (void 0).all();
      const completedTodos = todos2.filter((todo) => todo.completed);
      await Promise.all(completedTodos.map((todo) => (void 0).remove(todo.id)));
    }
  };
};
const DEFAULT_ERROR_MESSAGE = "An unexpected system error occurred. Please try again later.";
const DEFAULT_AUTO_HIDE_DURATION = 3e3;
const Snackbar = () => {
  const snackbar = document.getElementById("snackbar");
  return {
    show: (error, autoHideDuration = DEFAULT_AUTO_HIDE_DURATION) => {
      if (error instanceof Error) {
        const message = error.message || DEFAULT_ERROR_MESSAGE;
        const details = error.details || [];
        if (details && details.length > 0) {
          const formattedDetails = details.map((it) => `<div>${it}</div>`).join("");
          snackbar.innerHTML = `${message}
${formattedDetails}`;
        } else {
          snackbar.textContent = message;
        }
      } else if (typeof error === "string") {
        snackbar.textContent = error;
      } else {
        snackbar.textContent = DEFAULT_ERROR_MESSAGE;
        console.warn(`Warning: The '${error}' is unprocessable`);
      }
      snackbar.classList.add("show");
      setTimeout(() => snackbar.classList.remove("show"), autoHideDuration);
    }
  };
};
class UserSession {
  constructor(userProfileService) {
    this.userProfileService = userProfileService;
    this.subscribers = [];
    this.props = {
      userProfile: { name: "Guest", profilePictureUrl: "/profile-picture.png" }
    };
    this.refresh();
  }
  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedUserSession(this.props));
  }
  async refresh() {
    this.props = {
      userProfile: await this.userProfileService.get()
    };
    this.notify();
  }
  async updateProfilePicture(profilePicture) {
    this.props = {
      userProfile: await this.userProfileService.updateProfilePicture(profilePicture)
    };
    this.notify();
  }
  async logout() {
    this.props = {
      userProfile: await this.userProfileService.clear()
    };
    this.notify();
  }
}
class UserSessionView {
  constructor() {
    this.snackbar = Snackbar();
    this.userSessionContainer = document.querySelector(".user-session-container");
    this.loginGuide = document.querySelector(".login-guide");
    this.loginLink = document.querySelector(".login-guide a");
    this.userProfile = document.querySelector(".user-profile");
    this.username = document.querySelector(".user-profile strong");
    this.userProfilePicture = document.querySelector(".user-profile img");
    this.updateProfilePictureLink = document.querySelector(".update-profile-picture-link");
    this.logoutLink = document.querySelector(".logout-link");
    this.onLogin = null;
    this.onUpdateProfilePicture = null;
    this.onLogout = null;
    this.attachEventListeners();
  }
  attachEventListeners() {
    this.loginLink.addEventListener("click", (event) => {
      if (!this.onLogin) {
        console.warn("Warning: onLogin handler is not defined");
        return;
      }
      this.onLogin();
    });
    this.updateProfilePictureLink.addEventListener("click", (event) => {
      if (!this.onUpdateProfilePicture) {
        console.warn("Warning: onUpdateProfilePicture handler is not defined");
        return;
      }
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = "image/*";
      fileInput.style.display = "none";
      document.body.appendChild(fileInput);
      fileInput.click();
      fileInput.addEventListener("change", () => {
        if (fileInput.files.length > 0) {
          const file = fileInput.files[0];
          this.onUpdateProfilePicture(file);
        }
        document.body.removeChild(fileInput);
      });
    });
    this.logoutLink.addEventListener("click", (event) => {
      if (!this.onLogout) {
        console.warn("Warning: onLogout handler is not defined");
        return;
      }
      this.onLogout();
    });
  }
  onChangedFeatureToggles(featureToggles2) {
    this.userSessionContainer.style.display = featureToggles2.auth ? "block" : "none";
  }
  onChangedUserSession(userSession2) {
    const { userProfile } = userSession2 || {};
    if (userProfile) {
      this.loginGuide.style.display = "none";
      this.userProfile.style.display = "block";
      this.username.textContent = userProfile.name ?? "Guest";
      this.userProfilePicture.src = userProfile.profilePictureUrl ?? "";
    } else {
      this.loginGuide.style.display = "block";
      this.userProfile.style.display = "none";
    }
  }
  onError(error) {
    this.snackbar.show(error);
  }
}
class UserCount {
  constructor(userCountService) {
    this.userCountService = userCountService;
    this.subscribers = [];
    this.userCount = 1;
  }
  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedUserCount(this.userCount));
  }
  onChangedFeatureToggles(featureToggles2) {
    if (featureToggles2.onlineUsersCounter) {
      this.userCountService.connect((userCount2) => {
        this.userCount = userCount2;
        this.notify();
      });
    } else {
      this.userCountService.disconnect();
    }
  }
}
class UserCountView {
  constructor() {
    this.userCountContainer = document.querySelector(".user-count");
    this.userCount = document.querySelector(".user-count strong");
  }
  onChangedFeatureToggles(featureToggles2) {
    this.userCountContainer.style.display = featureToggles2.onlineUsersCounter ? "block" : "none";
  }
  onChangedUserCount(count) {
    this.userCount.textContent = count;
  }
}
const ENTER_KEY = "Enter";
const ESCAPE_KEY = "Escape";
class Todos {
  constructor(todoService) {
    this.todoService = todoService;
    this.subscribers = [];
    this.data = [];
    this.filter = "all";
  }
  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedTodos(this.data));
  }
  async refresh(filter = "all") {
    const todos2 = await this.todoService.all();
    this.data = todos2.filter(
      (todo) => filter === "all" || filter === "active" && !todo.completed || filter === "completed" && todo.completed
    );
    this.filter = filter;
    this.notify();
  }
  async toggleAll(completed) {
    const todos2 = await this.todoService.all();
    const tasks = todos2.map((todo) => this.todoService.edit({ ...todo, completed }));
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
}
class TodosView {
  constructor() {
    this.snackbar = Snackbar();
    this.newTodoInput = document.querySelector(".new-todo");
    this.toggleAllCheckbox = document.querySelector(".toggle-all");
    this.toggleAll = document.querySelector(".toggle-all-label");
    this.todoList = document.querySelector(".todo-list");
    this.todoCount = document.querySelector(".todo-count strong");
    this.filters = document.querySelector(".filters");
    this.clearCompletedButton = document.querySelector(".clear-completed");
    this.downloadTodosButton = document.querySelector(".download-todos");
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
    this.toggleAll.addEventListener("click", (event) => {
      if (!this.onToggleAll) {
        console.warn("Warning: onToggleAll handler is not defined");
        return;
      }
      this.toggleAllCheckbox.checked = !this.toggleAllCheckbox.checked;
      this.onToggleAll(this.toggleAllCheckbox.checked);
    });
    this.newTodoInput.addEventListener("keypress", (event) => {
      if (!this.onCreateTodo) {
        console.warn("Warning: onCreateTodo handler is not defined");
        return;
      }
      if (event.key === ENTER_KEY && this.newTodoInput.value.trim() !== "") {
        this.onCreateTodo(this.newTodoInput.value.trim());
        this.newTodoInput.value = "";
      }
    });
    this.todoList.addEventListener("click", (event) => {
      if (!this.onDeleteTodo) {
        console.warn("Warning: onDeleteTodo handler is not defined");
        return;
      }
      if (!this.onUpdateTodo) {
        console.warn("Warning: onUpdateTodo handler is not defined");
        return;
      }
      const target = event.target;
      const todoItem = target.closest("li");
      if (!todoItem) return;
      const id = todoItem.dataset.id;
      if (target.classList.contains("destroy")) {
        this.onDeleteTodo(id);
      } else if (target.classList.contains("toggle")) {
        const label = todoItem.querySelector("label");
        const checkbox = todoItem.querySelector(".toggle");
        this.onUpdateTodo(id, label.textContent, checkbox.checked);
      }
    });
    this.todoList.addEventListener("dblclick", (event) => {
      if (!this.onUpdateTodo) {
        console.warn("Warning: onUpdateTodo handler is not defined");
        return;
      }
      const target = event.target;
      const todoItem = target.closest("li");
      if (!todoItem) return;
      if (target.tagName === "LABEL") {
        const id = todoItem.dataset.id;
        const label = todoItem.querySelector("label");
        const checkbox = todoItem.querySelector(".toggle");
        const className = todoItem.className;
        const input = document.createElement("input");
        input.className = "edit";
        input.value = label.textContent;
        todoItem.className = `${className} editing`;
        todoItem.appendChild(input);
        input.focus();
        input.addEventListener("blur", () => {
          todoItem.className = className;
          todoItem.removeChild(input);
        });
        input.addEventListener("keyup", (event2) => {
          if (event2.key === ESCAPE_KEY) {
            todoItem.className = className;
            todoItem.removeChild(input);
          }
        });
        input.addEventListener("keypress", (event2) => {
          if (event2.key === ENTER_KEY) {
            this.onUpdateTodo(id, input.value, checkbox.checked);
          }
        });
      }
    });
    this.filters.addEventListener("click", (event) => {
      if (event.target.tagName === "A") {
        const selectedFilter = event.target.getAttribute("href").slice(2);
        const filters = document.querySelectorAll(".filters li a");
        filters.forEach((filter) => {
          if (filter.getAttribute("href") === `#/${selectedFilter}`) {
            filter.classList.add("selected");
            this.filter = filter.textContent.toLowerCase();
          } else {
            filter.classList.remove("selected");
          }
        });
        this.onFilterTodos(this.filter);
      }
    });
    this.clearCompletedButton.addEventListener("click", (event) => {
      if (!this.onClearCompletedTodos) {
        console.warn("Warning: onClearCompletedTodos handler is not defined");
        return;
      }
      this.onClearCompletedTodos();
    });
    this.downloadTodosButton.addEventListener("click", (event) => {
      if (!this.onDownloadTodos) {
        console.warn("Warning: onDownloadTodos handler is not defined");
        return;
      }
      const save = (blob, fileName) => {
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.setAttribute("href", url);
        link.setAttribute("download", fileName);
        link.style.visibility = "hidden";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      };
      this.onDownloadTodos((...args) => {
        if (args.length === 3) {
          const [content, contentType, fileName] = args;
          save(new Blob([content], { type: contentType }), fileName);
        } else if (args.length === 1) {
          const [url] = args;
          if (typeof url === "string" && url.startsWith("http")) {
            console.error("Warning: Invalid download url");
            return;
          }
          fetch(url, { headers: { "Accept": "text/csv" } }).then((response) => {
            let fileName = "todos.csv";
            const contentDisposition = response.headers.get("Content-Disposition");
            if (contentDisposition) {
              const matches = contentDisposition.match(/filename\*?=['"]?(?:UTF-\d['"]*)?([^;\r\n"']*)['"]?/i);
              if (matches && matches[1]) {
                fileName = matches[1];
              }
            }
            return response.blob().then((blob) => ({ blob, fileName }));
          }).then(({ blob, fileName }) => save(blob, fileName)).catch(this.onError.bind(this));
        } else {
          console.error("Warning: Unable to resolve download todos");
        }
      });
    });
  }
  onChangedTodos(todos2) {
    const completedCount = todos2.filter((todo) => todo.completed).length;
    this.todoList.innerHTML = todos2.map(
      (todo) => `<li data-id="${todo.id}" class="${todo.completed ? "completed" : ""}">
        <div class="view">
          <input class="toggle" type="checkbox" ${todo.completed ? "checked" : ""}>
          <label>${todo.text}</label>
          <button class="destroy"></button>
        </div>
      </li>`
    ).join("");
    this.todoCount.textContent = todos2.length;
    this.clearCompletedButton.style.display = completedCount > 0 ? "block" : "none";
  }
  onError(error) {
    this.snackbar.show(error);
  }
}
class TodosController {
  constructor(props, userSession2, todos2) {
    this.props = props || {};
    this.userSession = userSession2;
    this.todos = todos2;
  }
  bindTodosViewCallbacks(todosView2) {
    const onChangedTodos = todosView2.onChangedTodos.bind(todosView2);
    const onError = todosView2.onError.bind(todosView2);
    todosView2.onToggleAll = (completed) => {
      this.todos.toggleAll(completed).catch(onError);
    };
    todosView2.onCreateTodo = (text) => {
      this.todos.add(text).catch(onError);
    };
    todosView2.onUpdateTodo = (todoId, text, completed) => {
      if (todoId == null || todoId.trim() === "") {
        console.error("Update todo: todoId cannot be null, undefined, or empty");
        throw new Error("Please enter an ID to update a todo");
      }
      this.todos.edit(todoId, text, completed).catch(onError);
    };
    todosView2.onDeleteTodo = (todoId) => {
      if (todoId == null || todoId.trim() === "") {
        console.error("Delete todo: todoId cannot be null, undefined, or empty");
        throw new Error("Please enter an ID to delete a todo");
      }
      this.todos.remove(todoId).catch(onError);
    };
    todosView2.onFilterTodos = (filter) => {
      this.todos.refresh(filter).catch(onError);
    };
    todosView2.onClearCompletedTodos = () => {
      this.todos.clearCompleted().catch(onError);
    };
    todosView2.onDownloadTodos = (outputStream) => {
      if (this.props.downloadUrl) {
        outputStream(this.props.downloadUrl);
        return;
      }
      this.todos.all().then((todos2) => {
        const headers = "id,text,completed,createdAt\n";
        const rows = todos2.map((todo) => {
          const createdAt = new Date(todo.createdAt).toString();
          return `${todo.id},"${todo.text}",${todo.completed},${createdAt}`;
        }).join("\n");
        outputStream(headers + rows, "text/csv;charset=utf-8;", "todos.csv");
      });
    };
    this.todos.refresh().catch((error) => {
      onChangedTodos([]);
      onError(error);
    });
  }
  bindUserSessionViewCallbacks(userSessionView2) {
    const onError = userSessionView2.onError.bind(userSessionView2);
    userSessionView2.onLogin = () => {
      if (!this.props.loginUrl) {
        userSessionView2.onError("Login is not supported");
        console.warn("Warning: login-url is not defined");
        return;
      }
      document.location.href = this.props.loginUrl;
    };
    userSessionView2.onUpdateProfilePicture = (profilePicture) => {
      this.userSession.updateProfilePicture(profilePicture).catch(onError);
    };
    userSessionView2.onLogout = () => {
      if (this.props.logoutUrl) {
        document.location.href = this.props.logoutUrl;
      } else {
        this.userSession.logout().then(() => {
          if (!this.props.logoutSuccessUrl) {
            console.warn("Warning: logout-success-url is not defined");
            return;
          }
          document.location.href = this.props.logoutSuccessUrl;
        });
      }
    };
  }
}
class FeatureToggles {
  constructor(featureTogglesService) {
    this.featureTogglesService = featureTogglesService;
    this.subscribers = [];
    this.props = {
      auth: false,
      onlineUsersCounter: false
    };
    (async () => {
      try {
        this.props = await this.featureTogglesService.fetchAll();
        this.notify();
      } catch (error) {
        console.warn("Failed to fetch feature-toggles:", error);
      }
    })();
  }
  subscribe(subscriber) {
    this.subscribers.push(subscriber);
  }
  async notify() {
    this.subscribers.forEach((subscriber) => subscriber.onChangedFeatureToggles(this.props));
  }
  isAuth() {
    return this.props.auth;
  }
  isOnlineUsersCounter() {
    return this.props.onlineUsersCounter;
  }
}
const WebAPIFeatureTogglesService = (apiUrl = "/api/feature-toggles") => {
  const handleResponse = async (response) => {
    if (!response.ok) {
      if (response.status === 404) {
        console.warn("feature-toggles are not yet implemented on the server");
        return {
          auth: false,
          onlineUsersCounter: false
        };
      }
      const data = await response.json();
      const error = new Error(data.message ?? "Failed to process in server");
      error.name = data.error ?? "Unknown Error";
      error.details = (data.errors ?? []).map((it) => typeof it === "string" ? it : it.defaultMessage);
      throw error;
    }
    return response.json();
  };
  return {
    fetchAll: async () => {
      const response = await fetch(apiUrl);
      return await handleResponse(response);
    }
  };
};
const featureToggles = new FeatureToggles(WebAPIFeatureTogglesService());
const userSessionView = new UserSessionView();
const userCountView = new UserCountView();
const todosView = new TodosView();
const userSession = new UserSession(WebAPIUserProfileService());
userSession.subscribe(userSessionView);
const userCount = new UserCount(OnlineUserCountService());
userCount.subscribe(userCountView);
const todos = new Todos(WebAPITodosService());
todos.subscribe(todosView);
featureToggles.subscribe(userSessionView);
featureToggles.subscribe(userCount);
featureToggles.subscribe(userCountView);
featureToggles.notify();
const todosControler = new TodosController(
  {
    downloadUrl: "/todos",
    loginUrl: "/login",
    logoutUrl: "/logout",
    logoutSuccessUrl: null
  },
  userSession,
  todos
);
todosControler.bindTodosViewCallbacks(todosView);
todosControler.bindUserSessionViewCallbacks(userSessionView);
