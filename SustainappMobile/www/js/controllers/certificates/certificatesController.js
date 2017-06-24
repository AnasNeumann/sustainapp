/**
 * Controller pour la page des cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('certificatesController', function($scope, $ionicPopover, $state, sessionService, challengeService, coursService, fileService, listService, displayService) {
  
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadCertificatesPage();
        });
	
	/**
	 * Chargement initial de la page
	 */
	var loadCertificatesPage = function(){
		$scope.coursModel = {};
		$scope.coursModel.loaded = false;
		$scope.coursModel.filter = "";
		$scope.coursModel.langue = "";
		
		$scope.coursModel.moreCours = true;
		$scope.coursModel.startIndex = 0;
		$scope.coursModel.add = false;
		$scope.coursModel.allCours = [];
		
		$scope.coursModel.emptyPicture = true;
		$scope.coursModel.editFile = false;
		$scope.coursModel.file = null;
		$scope.coursModel.displayFile = "";
		
		$scope.coursModel.title = "";
		$scope.coursModel.about = "";
		$scope.coursModel.levelMin = 0;
		$scope.coursModel.type = {};
		$scope.coursModel.types = [];
		$scope.coursModel.allErrors = [];
		
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
		$ionicPopover.fromTemplateUrl('templates/certificates/popover-langues.html', {
		    scope: $scope
		  }).then(function(popover) {
		    $scope.popoverLanguages = popover;
		  });
		$ionicPopover.fromTemplateUrl('templates/certificates/popover-chooseLangues.html', {
		    scope: $scope
		  }).then(function(popover) {
		    $scope.popoverChooseLanguages = popover;
		  });
		
		/**
		 * Chargement des langues
		 */
		$scope.languages = [
			{
				'code' : 'fr',
				'name' : 'filter.fr'
			},
			{
				'code' : 'en',
				'name' : 'filter.en'
			}
		];
		$scope.currentLangue = $scope.languages[0];
		
		/**
		 * Chargement des types
		 */
		challengeService.types().then(function(response){
			if(response.data.code == 1) {
				$scope.coursModel.loaded = true;
				$scope.coursModel.type = response.data.types[0];
				$scope.types = response.data.types;
				$scope.levels = [0,1,2,3,4,5,6,7,8,9];
			}
		});
		$scope.getMoreCours();
	};
	
	/**
	 * fonction de chargement infinity scroll de plus de cours
	 */
	$scope.getMoreCours = function(){
		$scope.coursModel.moreCours = true;
		coursService.getAll($scope.coursModel.startIndex).then(function(response){
			result = response.data;
			$scope.coursModel.moreCours = false;
			$scope.$broadcast('scroll.infiniteScrollComplete');
			if(result.code == 1 && result.cours.length >0) {
				//$scope.coursModel.startIndex += result.challenges.length;
				$scope.coursModel.startIndex += 1;
				$scope.coursModel.allCours = listService.addWithoutDoublons($scope.coursModel.allCours, result.cours);
			} else {
				$scope.coursModel.moreCours = false;
			}
		}, function(response){
			sessionService.refresh($scope.getMoreCours);
		});
	}
	
	/**
	 * Ouverture du menu de choix de la langue du nouveau cours
	 */
	$scope.openChooseLangues = function($event){
		$scope.popoverChooseLanguages.show($event);
	}
	
	/**
	 * fonction d'ouverture du menu de choix du filtre de langues
	 */
	$scope.openLanguages = function($event){
		$scope.popoverLanguages.show($event);
	}
	
	/**
	 * fonction d'ouverture du menu de choix du filtre de types
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
		$scope.coursModel.type = type;
	}
	
	/**
	 * Fonction de changement de filtre d'affichage
	 */
	$scope.changeFilter = function(filter){
		$scope.popoverFilter.hide();
		$scope.coursModel.filter = filter;
	}
	
	/**
	 * Fonction de changement de filtre de langue
	 */
	$scope.changeLangue = function(filter){
		$scope.popoverLanguages.hide();
		$scope.coursModel.langue = filter;
	}

	/**
	 * fonction de changement du level minimum
	 */
	$scope.changeLevelMin = function(level){
		$scope.popoverLevel.hide();
		$scope.coursModel.levelMin = level;
	}
	
	/**
	 * Choix d'une langue pour le nouveau cours
	 */
	$scope.changeNewLangue = function(langue){
		$scope.popoverChooseLanguages.hide();
		$scope.currentLangue = langue;
	}
	
	/**
	 * fonction d'ajout d'un nouveau cours
	 */
	$scope.addCours = function(){
		var data = new FormData();
		if(null != $scope.coursModel.file) {
			data.append("file", $scope.coursModel.file);
		}
		data.append("title", $scope.coursModel.title);
		data.append("about", $scope.coursModel.about);
		data.append("levelMin", $scope.coursModel.levelMin);
		data.append("type", $scope.coursModel.type.id);
		data.append("langue", $scope.currentLangue.code);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.create(data).success(function(result) {
			if(result.code == 1){
				$scope.coursModel.emptyPicture = true;
				$scope.coursModel.editFile = false;
				$scope.coursModel.file = null;
				$scope.coursModel.displayFile = "";					
				$scope.coursModel.title = "";
				$scope.coursModel.about = "";
				$scope.coursModel.levelMin = 0;
				$scope.coursModel.allErrors = [];
				$state.go('tab.cours-one', {'id' : result.id});
			} else {
				$scope.coursModel.allErrors = result.errors;
			}
		}).error(function(error){
	    	sessionService.refresh($scope.addCours);
	    });		
	}

	/**
	 * Modification de l'image pour le nouveau challenge [mobile mode]
	 */
	$scope.chooseFile = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
			$scope.coursModel.file = imageData;
			$scope.coursModel.displayFile = "data:image/jpeg;base64,"+imageData;
			$scope.coursModel.emptyPicture = false;
			$scope.coursModel.editFile = false;
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
        		$scope.coursModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
            	$scope.coursModel.displayFile = e.target.result;
            	$scope.coursModel.emptyPicture = false;   
            });	
        }
        reader.readAsDataURL(input.files[0]);  
	}

});