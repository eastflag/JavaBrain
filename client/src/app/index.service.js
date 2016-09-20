/**
 * Created by eastflag on 2016-09-20.
 */
/**
 * Created by eastflag on 2016-08-31.
 */
(function() {
  'use strict';

  angular
    .module('clientApp')
    .service('HttpSvc', HttpService);

  function HttpService($http, HOST) {
    this.addAnswer  = function (params) {
      return $http({
        url: HOST + '/api/addAnswer', method: 'POST', headers: {'Content-Type': 'application/json'}, data: params
      });
    };
  }
})();
