'use strict';

/**
 * @ngdoc overview
 * @name homeApp
 * @description
 * # homeApp
 *
 * Main module of the application.
 */
angular
  .module('clientApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngMaterial'
  ])
  .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/main/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main',
        data: {
          requireLogin: false
        }
      })
    //자바 메뉴--------------------------------------------------
    .state('java', {
      url: '/java',
      abstract: true,
      templateUrl: 'app/java/index.html',
      data: {
        requireLogin: false
      }
    })
    .state('java.main', {
      url: '',
      templateUrl: 'app/java/main.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.anonymous', {
      url: '/anonymous',
      templateUrl: 'app/java/anonymous.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.collection', {
      url: '/collection',
      templateUrl: 'app/java/collection.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.generic', {
      url: '/generic',
      templateUrl: 'app/java/generic.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.thread', {
      url: '/thread',
      templateUrl: 'app/java/thread.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    //자바스크립트 메뉴--------------------------------------------------
    .state('javascript', {
      url: '/javascript',
      templateUrl: 'app/javascript/index.html',
      abstract: true
    })
    .state('javascript.main', {
      url: '',
      templateUrl: 'app/javascript/main.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    //안드로이드 메뉴--------------------------------------------------
    .state('android', {
      url: '/android',
      abstract: true,
      templateUrl: 'app/android/index.html',
    })
    .state('android.main', {
      url: '',
      templateUrl: 'app/android/main.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.1', {
      url: '/1',
      templateUrl: 'app/android/1.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.2', {
      url: '/2',
      templateUrl: 'app/android/2.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.3', {
      url: '/3',
      templateUrl: 'app/android/3.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.4', {
      url: '/4',
      templateUrl: 'app/android/4.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.5', {
      url: '/5',
      templateUrl: 'app/android/5.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.6', {
      url: '/6',
      templateUrl: 'app/android/6.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.7', {
      url: '/7',
      templateUrl: 'app/android/7.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('android.8', {
      url: '/8',
      templateUrl: 'app/android/8.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    //게임 메뉴--------------------------------------------------
    .state('game', {
      url: '/game',
      abstract: true,
      templateUrl: 'app/game/index.html'
    })
    .state('game.main', {
      url: '',
      templateUrl: 'app/game/main.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    });
    $urlRouterProvider.otherwise('/');
  })

  .run(function ($rootScope) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {

    });
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){

    });
    $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error){

    });
    $rootScope.$on('$stateNotFound', function(event, unfoundState, fromState, fromParams){

    });
  });