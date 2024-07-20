const DEFAULT_ERROR_MESSAGE = 'An unexpected system error occurred. Please try again later.';
const DEFAULT_AUTO_HIDE_DURATION = 3000;

const Snackbar = () => {
  const snackbar = document.getElementById('snackbar');

  return {
    show: (error, autoHideDuration = DEFAULT_AUTO_HIDE_DURATION) => {
      if (error instanceof Error) {
        snackbar.textContent = error.message || DEFAULT_ERROR_MESSAGE;
      } else if (typeof error === 'string') {
        snackbar.textContent = error;
      } else {
        snackbar.textContent = DEFAULT_ERROR_MESSAGE;
        console.warn(`Warning: The '${error}' is unprocessable`);
      }
      
      snackbar.classList.add('show');      
      setTimeout(() => snackbar.classList.remove('show'), autoHideDuration);
    }
  };
}

export { Snackbar };