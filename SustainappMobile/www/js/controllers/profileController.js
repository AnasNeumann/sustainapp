/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('profileController', function($scope, $stateParams, session, profileService) {
		
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
				loadProfile();
	        });
		
		/**
		 * Fonction initiale d'affichage de la page
		 */
		var loadProfile = function(){
			$scope.profileModel = {};
			$scope.profileModel.profile = {};
			$scope.profileModel.modeRead = true; 
			$scope.profileModel.loaded = false;
			$scope.profileModel.owner = false;
			profileService.getById($stateParams.id).then(function(response){
				if(response.data.code == 1) {
	    			 $scope.profileModel.profile = response.data.profiles[0];
	    			 $scope.profileModel.profileTemp = response.data.profiles[0];
		    		 $scope.profileModel.loaded = true;
		    		 $scope.profileModel.allErrors = [];	
		    		 if(response.data.profiles[0].id == session.profile.id){
		    			 $scope.profileModel.owner = true;
		    		 }
	    		 }
	     	  });
	    };
	    
				
	});