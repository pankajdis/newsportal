(function(){
    var toggleButton = document.querySelector('.toggle-childpages');
    if(toggleButton) {
       toggleButton.addEventListener('click', function(){
           var extraContainer = document.querySelector('.childpages-extra');
           if (extraContainer.style.display === 'none') {
              extraContainer.style.display = 'block';
              toggleButton.textContent = 'Hide Pages';
           } else {
               extraContainer.style.display = 'none';
              toggleButton.textContent = 'View All Pages';
           }
       });
    }
})();