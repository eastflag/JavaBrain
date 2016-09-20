/**
 * Created by eastflag on 2016-09-19.
 */
angular.module('clientApp')
  .controller('NavigationCtrl', function ($mdSidenav) {
    var self = this;

    self.toggleList = toggleList;
    self.closeSideNav = closeSideNav;

    function toggleList() {
      $mdSidenav('right').toggle();
    };

    function closeSideNav() {
      if($mdSidenav('right').isOpen) {
        $mdSidenav('right').close();
      }
    }
  });
