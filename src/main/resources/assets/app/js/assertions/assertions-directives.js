'use strict';

angular.module('service-testing-tool').directive('requestResponseTextareas', function () {
  return {
    link: function(scope, element, attrs) {
      //  Use nearest absolute height node to determine reqResTextareas height.
      //  Chose window object.
      //  Not able to use page-wrapper node as it's height is dynamically changed by
      //  startbootstrap-sb-admin-2 javascript on window resize.
      var topOffset = 51 + 76 + 36;     // navbar 51px, page-header 76px, tab heading 36px;
      var tabContentsHeight = window.innerHeight - topOffset;
      element.height(tabContentsHeight * 0.75);

      var toggleButton = angular.element(document.getElementById('assertionAreaToggleButton'));
      toggleButton.bind('click', function() {
        var newHeight = element.height() + document.getElementById('assertionsArea').offsetHeight;
        element.height(newHeight);
        scope.showAssertionsArea = !(scope.showAssertionsArea);
        scope.$apply();
      });

      scope.$on('assertionsAreaLoaded', function () {
        var newHeight = element.height() - document.getElementById('assertionsArea').offsetHeight;
        element.height(newHeight);
      })
    }
  };
});