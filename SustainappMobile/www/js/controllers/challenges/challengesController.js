/**
 * Controller d'affichage de tout les challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('challengesController', 
			function($scope, $ionicPopover, $state, sessionService, challengeService, fileService, listService, displayService) {
		
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
			$scope.challengesModel.filter = "";
			
			$scope.challengesModel.moreChallenges = true;
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
			
			$scope._isNotMobile = displayService.isNotMobile;
						
			$ionicPopover.fromTemplateUrl('templates/common/popover-level.html', {
			    scope: $scope
			  }).then(function(popover) {
			    $scope.popoverLevel = popover;
			  });
			$ionicPopover.fromTemplateUrl('templates/common/popover-type.html', {
			    scope: $scope
			  }).then(function(popover) {
			    $scope.popoverType = popover;
			  });
			$ionicPopover.fromTemplateUrl('templates/common/popover-filter.html', {
			    scope: $scope
			  }).then(function(popover) {
			    $scope.popoverFilter = popover;
			  });
			
			/**
			 * Chargement des types
			 */
			challengeService.types().then(function(response){
				if(response.data.code == 1) {
					$scope.challengesModel.loaded = true;
					$scope.challengesModel.type = response.data.types[0];
					$scope.types = response.data.types;
					$scope.levels = [0,1,2,3,4,5,6,7,8,9];
				}
			});
			$scope.getMoreChallenges();
		};
		
		/**
		 * fonction de chargement infinity scroll de plus de challenges
		 */
		$scope.getMoreChallenges = function(){
			$scope.challengesModel.load = true;
			challengeService.getAll($scope.challengesModel.startIndex).then(function(response){
				result = response.data;
				$scope.challengesModel.load = false;
				$scope.$broadcast('scroll.infiniteScrollComplete');
				if(result.code == 1 && result.challenges.length >0) {
					$scope.challengesModel.startIndex += result.challenges.length;
					$scope.challengesModel.allChallenges = listService.addWithoutDoublons($scope.challengesModel.allChallenges, result.challenges);
				} else {
					$scope.challengesModel.moreChallenges = false;
				}
			});
		}
		
		
		/**
		 * fonction d'ouverture du menu de choix du filtre
		 */
		$scope.openFilter = function($event){
			$scope.popoverFilter.show($event);
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
		 * Fonction de changement de filtre d'affichage
		 */
		$scope.changeFilter = function(filter){
			$scope.popoverFilter.hide();
			$scope.challengesModel.filter = filter;
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
			var data = new FormData();
			if(null != $scope.challengesModel.file) {
				data.append("file", $scope.challengesModel.file);
			}
			data.append("name", $scope.challengesModel.name);
			data.append("about", $scope.challengesModel.about);
			data.append("endDate", $scope.challengesModel.endDate);
			data.append("levelMin", $scope.challengesModel.levelMin);
			data.append("teamEnabled", $scope.challengesModel.teamEnabled);
			data.append("type", $scope.challengesModel.type.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			challengeService.create(data).success(function(result) {
				if(result.code == 1){
					$scope.challengesModel.emptyPicture = true;
					$scope.challengesModel.editFile = false;
					$scope.challengesModel.file = null;
					$scope.challengesModel.displayFile = "";					
					$scope.challengesModel.name = "";
					$scope.challengesModel.about = "";
					$scope.challengesModel.endDate = "";
					$scope.challengesModel.levelMin = 0;
					$scope.challengesModel.allErrors = [];
					$state.go('tab.challenge-one', {'id' : result.id});
				} else {
					$scope.challengesModel.allErrors = result.errors;
				}
			});			
		}

		/**
		 * Modification de l'image pour le nouveau challenge [mobile mode]
		 */
		$scope.chooseFile = function(newFile){
			fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
				$scope.challengesModel.file = imageData;
				$scope.challengesModel.displayFile = "data:image/jpeg;base64,"+imageData;
				$scope.challengesModel.emptyPicture = false;
				$scope.challengesModel.editFile = false;
			 }, function(err) {
			 });
		};
		
		/**
		 * Modification de l'image pour le nouveau challenge [desktop mode]
		 */
		$scope.desktopChooseFile = function(input){
			var reader = new FileReader();
            reader.onload = function (e) {
            	$scope.$apply(function () {
            		$scope.challengesModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
                	$scope.challengesModel.displayFile = e.target.result;
                	$scope.challengesModel.emptyPicture = false;   
                });	
            }
            reader.readAsDataURL(input.files[0]);  
		}

	});