'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('MainCtrl', function ($firebaseObject, $firebaseArray) {
    var self = this;

    init();

    function init() {
      var userRef = firebase.database().ref().child("users").child('eastflag');
      //self.user = $firebaseObject(userRef);
      //console.log(self.user);
      self.userList = $firebaseArray(firebase.database().ref().child("users"));
      console.log(self.userList);
      console.log(self.userList.length); //0 why? Firebase는 자체가 promise다. 데이터를 비동기로 가져와서 뿌려주므로, 아래에서는 0.

      self.userList.$loaded().then(function () {
        console.log('loaded:' + self.userList.length);
      }.bind(this));

      //쓰기

      //users에 hong3이라는 키값을 만든 다음에 값을 쓴다. push는 key를 임의대로 만든다.
      //firebase.database().ref().child("users").child("hong3").set({name: "hong3", email:"hong@hong.com"});
    }
  });
