import { defineConfig } from 'vite'
import { resolve } from 'path'

export default defineConfig({
  build: {
    minify: false,
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'index.html'),
        todos: resolve(__dirname, 'pages/todos.html'),
        login: resolve(__dirname, 'pages/login.html'),
        error: resolve(__dirname, 'pages/error.html'),
      },
    }
  },
  test: {
    environment: 'jsdom',
    globals: true,
  },
})
