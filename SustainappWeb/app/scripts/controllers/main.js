'use strict';

/**
 * @ngdoc function
 * @name sustainappWebApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sustainappWebApp
 */
angular.module('sustainappWebApp')
  .controller('MainCtrl', function ($scope, $http, monService, recherche) {
	  $scope.maVar = "";
      
	  /**
	   * Utilisation directe de http 
	   */
	  $http.get('http://localhost:8085/profile').then(function(response) {
          $scope.result = response.data.result;
      });
      
	  /**
	   * Service réutilisable
	   */
      var profileService = function(){
    	  $scope.loadingProfiles = true;
    	  monService.allProfiles().then(function(response){
    		  $scope.profiles = response.data;
    		  $scope.loadingProfiles = false;
    	  });
      };
      
      /**
	   * Service réutilisable
	   */
      var rechercheService = function(){
    	  $scope.loadingProfiles = true;
    	  recherche.recherche().then(function(response){
    		  $scope.profiles = response.data;
    		  console.log($scope.profiles);
    		  $scope.loadingProfiles = false;
    	  });
      };
      
      /**
       * en cas de rechargement de page on recharge les films
       */
      $scope.pageChanged = function(){
    	  profileService();
      };
      rechercheService();
      profileService();
      
 });
