/**
 * Essai de controller d'affichage d'une liste de profil
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('challengesController', function($scope, $ionicPopover, sessionService, challengeService, fileService, listService) {
		
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
				loadChallengesPage();
	        });
		
		/**
		 * Chargement initial de la page
		 */
		var loadChallengesPage = function(){
			$scope.challengesModel = {};
			$scope.challengesModel.loaded = false;
			
			$scope.challengesModel.moreTeams = true;
			$scope.challengesModel.startIndex = 0;
			$scope.challengesModel.add = false;
			$scope.challengesModel.allChallenges = [];
			
			$scope.challengesModel.emptyPicture = true;
			$scope.challengesModel.editFile = false;
			$scope.challengesModel.file = null;
			$scope.challengesModel.displayFile = "";
			
			$scope.challengesModel.name = "";
			$scope.challengesModel.about = "";
			$scope.challengesModel.endDate = "";
			$scope.challengesModel.teamEnabled = true;
			$scope.challengesModel.levelMin = 0;
			$scope.challengesModel.type = {};
			$scope.challengesModel.types = [];
			$scope.challengesModel.allErrors = [];
						
			$ionicPopover.fromTemplateUrl('templates/challenges/popover-level.html', {
			    scope: $scope
			  }).then(function(popover) {
			    $scope.popoverLevel = popover;
			  });
			$ionicPopover.fromTemplateUrl('templates/challenges/popover-type.html', {
			    scope: $scope
			  }).then(function(popover) {
			    $scope.popoverType = popover;
			  });
			
			/**
			 * Chargement des types
			 */
			challengeService.types().then(function(response){
				if(response.data.code == 1) {
					$scope.challengesModel.loaded = true;
					$scope.challengesModel.type = response.data.types[0];
					$scope.challengesModel.types = response.data.types;
					$scope.challengesModel.levels = [0,1,2,3,4,5,6,7,8,9];
				}
			});
			$scope.getMoreChallenges();
		};
		
		/**
		 * fonction de chargement infinity scroll de plus de challenges
		 */
		$scope.getMoreChallenges = function(){
			//TODO
		}
		
		/**
		 * fonction d'ouverture du menu de choix du level min
		 */
		$scope.openLevelMin = function($event){
			$scope.popoverLevel.show($event);
		}
		
		/**
		 * fonction d'ouverture du menu de choix du type de challenge
		 */
		$scope.openTypes = function($event){
			$scope.popoverType.show($event);
		}
		
		/**
		 * fonction de changement du type de challenge
		 */
		$scope.changeType = function(type){
			$scope.popoverType.hide();
			$scope.challengesModel.type = type;
		}
		
		/**
		 * fonction de changement du level minimum
		 */
		$scope.changeLevelMin = function(level){
			$scope.popoverLevel.hide();
			$scope.challengesModel.levelMin = level
		}
		
		/**
		 * fonction d'ajout d'un nouveau challenge
		 */
		$scope.addChallenge = function(){
			//TODO
		}

	});