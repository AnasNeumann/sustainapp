/**
 * Essai de controller d'affichage d'une liste de profil
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('challengesController', function($scope, $http, Chats, config, session) {
		/*
		var profileService = function(){
	  	  $scope.loadingProfiles = true;
	  	  profilService.allProfiles().then(function(response){
	  		  $scope.profiles = response.data;
	  		  $scope.loadingProfiles = false;
	  	  });
	    };
	
	  profileService();
	  */
	  $scope.chats = Chats.all();
	  $scope.remove = function(chat) {
	    Chats.remove(chat);
	  };
	  
		/**
		 * fonction de verification si on est connecté executée au démarage de l'application
		 *//*
		$scope.getSession = function(){
			var data = new FormData();
			data.append("sessionId", session.id);
			data.append("sessionToken", session.token);
			
			$http.post(config.localServer+"/session", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	console.log(result);
		    });

		}
		$scope.getSession();
	  */
	});