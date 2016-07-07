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
    //'ngTouch',
    'ui.sortable',
    'ui.bootstrap',
    'ngMaterial',
    'angular-storage',
    'angularGrid',
    'akoenig.deckgrid',
    'angular-jwt',
    'xeditable',
    'ui.tree'
  ])
  .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'views/main.html',
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
      templateUrl: 'views/java/index.html',
      data: {
        requireLogin: false
      }
    })
    .state('java.main', {
      url: '',
      templateUrl: 'views/java/main.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.anonymous', {
      url: '/anonymous',
      templateUrl: 'views/java/anonymous.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.collection', {
      url: '/collection',
      templateUrl: 'views/java/collection.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.generic', {
      url: '/generic',
      templateUrl: 'views/java/generic.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    .state('java.thread', {
      url: '/thread',
      templateUrl: 'views/java/thread.html',
      controller: function($scope) {

      },
      data: {
        requireLogin: false
      }
    })
    //자바스크립트 메뉴--------------------------------------------------
    .state('javascript', {
      url: '/javascript',
      templateUrl: 'views/javascript.html',
      controller: 'JavascriptCtrl',
      controllerAs: 'javascript',
      data: {
        requireLogin: false
      }
    })
    //안드로이드 메뉴--------------------------------------------------
    .state('android', {
      url: '/android',
      templateUrl: 'views/android.html',
      controller: 'AndroidCtrl',
      controllerAs: 'android',
      data: {
        requireLogin: false
      }
    })
    //게임 메뉴--------------------------------------------------
    .state('game', {
      url: '/game',
      templateUrl: 'views/game.html',
      controller: 'GameCtrl',
      controllerAs: 'game',
      data: {
        requireLogin: false
      }
    });
    $urlRouterProvider.otherwise('/');

    //enable cross domain calls
    //$httpProvider.defaults.useXDomain = true;

    //인증처리 인터셉터 주입
    $httpProvider.interceptors.push('authInjector');
  })
  .factory('authInjector', function($rootScope, $window, store) {
    var authInjector = {
      request: function (config) {
        if($rootScope.isAuthenticated()) {
          config.headers['X-Auth'] = store.get('token');
          /*          $.each(config.headers, function(k, v) {
           console.log( k + "    " + v);
           });*/
        }
        return config;
      },
      response: function (response) {
        var auth = response.config.headers['X-Auth'];
        //console.log('response: ' + response.config.headers['X-Auth']);
        if (auth !== undefined) {
          store.set('token', response.config.headers['X-Auth']);
        }
        return response;
      },
      responseError: function(rejection) {
        if (rejection.status === 401) {
          //로그인창 띄우기
          $rootScope.$broadcast('login');
          $window.alert('오래동안 사용하지 않아 세션이 만료되었습니다.')
        } else {
          return rejection;
        }
      }
    };
    return authInjector;
  })
  .run(function ($rootScope, store, jwtHelper) {
    $rootScope.mAuth = false;
    $rootScope.role = 0;

    $rootScope.isAuthenticated = function () {
      if (store.get('token') === null || store.get('token') === undefined) {
        $rootScope.mAuth = false;
        $rootScope.role = 0;
        return false;
      } else {
        if (jwtHelper.isTokenExpired(store.get('token'))) {
          store.remove('token');
          store.remove('user');
          $rootScope.mAuth = false;
          $rootScope.role = 0;
          return false;
        } else {
          $rootScope.mAuth = true;
          $rootScope.role = store.get('user').member_type;
          $rootScope.member_id = store.get('user').member_id;
          return true;
        }
      }
    };

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
      //console.log('stateChangeStart:' + toState.url);
      var requireLogin = toState.data.requireLogin;
      //console.log('requireLogin:' + requireLogin);
      //console.log('isAuth:' + $rootScope.isAuthenticated());

      if(requireLogin && !$rootScope.isAuthenticated()) {
        event.preventDefault();
        //로그인창 띄우기, 로그인 성공후 이동해야 할 경로를 두번째 인수로 넘겨준다.
        $rootScope.$broadcast('login', {url: toState.name, params: toParams});
      }
    });
    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){

    });
    $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, error){

    });
    $rootScope.$on('$stateNotFound', function(event, unfoundState, fromState, fromParams){

    });
  });
