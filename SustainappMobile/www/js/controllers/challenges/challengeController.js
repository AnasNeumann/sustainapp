/**
 * Controller pour l'affichage d'un challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('challengeController', function($scope, $stateParams, $ionicModal, $state, sessionService, fileService, challengeService) {

	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadChallenge();
        });
	
	/**
	 * Fonction de chargement de toutes les informations sur le challenge
	 */
	var loadChallenge = function(){
		$scope.challengeModel = {};
		$scope.challengeModel.loaded = false;
		$scope.title = "...";
		challengeService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {	
				$scope.challengeModel.loaded = true;
				$scope.title = result.challenge.name;
			}
		});
	};
	
	
});