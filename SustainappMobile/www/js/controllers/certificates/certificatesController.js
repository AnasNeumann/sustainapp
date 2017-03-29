/**
 * Essai de controller de databinding
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
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
		
		$scope.coursModel.moreCours = true;
		$scope.coursModel.startIndex = 0;
		$scope.coursModel.add = false;
		$scope.coursModel.allCours = [];
		
		$scope.coursModel.emptyPicture = true;
		$scope.coursModel.editFile = false;
		$scope.coursModel.file = null;
		$scope.coursModel.displayFile = "";
		
		$scope.coursModel.name = "";
		$scope.coursModel.about = "";
		$scope.coursModel.endDate = "";
		$scope.coursModel.teamEnabled = true;
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
		$scope.coursModel.moreCours = false;
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
	 * fonction de changement du level minimum
	 */
	$scope.changeLevelMin = function(level){
		$scope.popoverLevel.hide();
		$scope.coursModel.levelMin = level
	}
	
	/**
	 * fonction d'ajout d'un nouveau challenge
	 */
	$scope.addCours = function(){
			
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