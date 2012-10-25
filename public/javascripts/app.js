'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: LoginCtrl});  	
    $routeProvider.when('/showLuckyColor', {templateUrl: 'partials/showLuckyColor.html', controller: ShowColorCtrl});
    $routeProvider.otherwise({redirectTo: '/login'});
  }]);
