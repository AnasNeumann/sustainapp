/**
 * Controller de la visualisation des données
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('profilesDataController', function($scope, sessionService, administrationService) {
		
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadModel();
        });
		
		/**
		 * Chargement du model
		 */
		var loadModel  = function(){
			$scope.model = {};
			$scope.model.loaded = false;
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			administrationService.profiles(data).success(function(result) {
				$scope.model.loaded = true;
				$scope.model.average = result.average;
				$scope.model.totalProfiles = result.totalProfiles;
				$scope.model.totalTeams = result.totalTeams;
			});
		};
	});