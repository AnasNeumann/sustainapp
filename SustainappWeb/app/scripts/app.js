'use strict';

/**
 * @ngdoc overview
 * @name sustainappWebApp
 * @description
 * # sustainappWebApp
 *
 * Main module of the application.
 */
angular
  .module('sustainappWebApp', [
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
