'use strict';

/**
 * @ngdoc function
 * @name sustainappWebApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sustainappWebApp
 */
angular.module('sustainappWebApp')
  .controller('MainCtrl', function ($scope, $http) {
	  $scope.maVar = "";
      $http.get('http://localhost:8085/profile').then(function(response) {
          $scope.result = response.data.result;
      });
  });
