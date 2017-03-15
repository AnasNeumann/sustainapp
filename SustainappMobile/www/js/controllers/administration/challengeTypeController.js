/**
 * Controller for challengeType management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('challengeTypeController', function($scope, sessionService, challengeTypeService) {
	
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
				loadAllChallengeType();
	        });
		
		/**
		 * Chargement serveur de tout les types de challenges
		 */
		var loadAllChallengeType = function(){
			$scope.challengeTypeModel = {};
		};
		
	});
