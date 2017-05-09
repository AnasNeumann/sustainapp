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
			$scope.model.profilesaAgeData = [];
			$scope.model.profilesAgeLabels = [];
			$scope.model.profilesLevelData = [];
			$scope.model.profilesLevelLabels = [];
			$scope.model.teamsLevelData = [];
			$scope.model.teamsLevelLabels = [];
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			administrationService.profiles(data).success(function(result) {
				$scope.model.loaded = true;
				$scope.model.average = result.average;
				$scope.model.totalProfiles = result.totalProfiles;
				$scope.model.totalTeams = result.totalTeams;
				for(elt in result.profileByAge){
					$scope.model.profilesAgeLabels.push(elt);
					$scope.model.profilesaAgeData.push(result.profileByAge[elt]);
				}
				for(elt in result.profileByLevel){
					$scope.model.profilesLevelLabels.push("Level "+elt);
					$scope.model.profilesLevelData.push(result.profileByLevel[elt]);
				}
				for(elt in result.teamByLevel){
					$scope.model.teamsLevelLabels.push("Level "+elt);
					$scope.model.teamsLevelData.push(result.teamByLevel[elt]);
				}
			});
		};
	});