document.addEventListener('DOMContentLoaded', function () {

  const darkModeToggle = document.querySelector('#toggle_dark');
  const darkModeIcon = document.querySelector('.dark_icon');
  const lightModeIcon = document.querySelector('.light_icon');

  const applyDarkModeStyles = () => {
    document.body.dataset.dark_mode = 'on';
    localStorage.setItem("dark_mode", "on");
  };

  const applyLightModeStyles = () => {
    document.body.dataset.dark_mode = 'off';
    localStorage.setItem("dark_mode", "off");
  };

  if (darkModeToggle) {
    // Check user preference from localStorage
    const userPrefersDarkMode = localStorage.getItem('dark_mode') === 'on';

    // Apply styles based on user preference
    if (userPrefersDarkMode) {
      applyDarkModeStyles();
      darkModeToggle.checked = true;
    } else {
      applyLightModeStyles();
    }

    // Toggle styles when dark mode icon is clicked
    darkModeIcon.addEventListener("click", applyDarkModeStyles);
    
    // Toggle styles when light mode icon is clicked
    lightModeIcon.addEventListener("click", applyLightModeStyles);

    // Toggle styles when the toggle input is changed
    darkModeToggle.addEventListener("change", function () {
      if (darkModeToggle.checked) {
        applyDarkModeStyles();
      } else {
        applyLightModeStyles();
      }
    });
  } else {
    localStorage.removeItem("dark_mode");
  }
});