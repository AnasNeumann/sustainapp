/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('profileController', function($scope, session, profileService) {
		
		/**
		 * Fonction initiale d'affichage de la page
		 */
		var initProfileModel = function(){
			$scope.profileModel = {};
			$scope.profileModel.loading = true;
			$scope.profileModel.modeRead = true;
			$scope.profileModel.firstName = "";
			$scope.profileModel.lastName = "";
			$scope.profileModel.bornDate = "";
			$scope.profileModel.allErrors = [];
		};
		initProfileModel();
		
		
		
	});