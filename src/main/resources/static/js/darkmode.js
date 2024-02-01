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

    const userPrefersDarkMode = localStorage.getItem('dark_mode') === 'on';

    if (userPrefersDarkMode) {
      applyDarkModeStyles();
      darkModeToggle.checked = true;
    } else {
      applyLightModeStyles();
    }

    darkModeIcon.addEventListener("click", applyDarkModeStyles);
    lightModeIcon.addEventListener("click", applyLightModeStyles);
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