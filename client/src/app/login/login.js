/**
 * Created by eastflag on 2016-11-22.
 */
angular.module('clientApp')
  .controller('LoginCtrl', function (HttpSvc) {
    var self = this;

    self.naverUrl = null;
    HttpSvc.getSocialLogin()
      .success(function (value) {
        self.naverUrl = value.naver;
      })
  });
