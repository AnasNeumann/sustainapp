/**
 * Controller pour l'affichage principal d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('coursController', function($scope, $ionicPopover, $stateParams, $state, $ionicModal, sessionService, coursService, fileService, listService, displayService) {

	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadCours();
        });
		
	/**
	 * Chargement des informations du cours
	 */
	var loadCours = function(){
		$scope.coursModel = {};
		$scope.coursModel.loaded = false;
		$scope.title = "...";

		$scope.coursModel.emptyFile = true;
		$scope.coursModel.edit = false;
		$scope.coursModel.pictureEdit = false;
		$scope.coursModel.file  = null;
		
		$scope.deleteType = true;
		$scope.eltToDelete  = {};
		
		$ionicPopover.fromTemplateUrl('templates/common/popover-level.html', {
		    scope: $scope
		  }).then(function(popover) {
		    $scope.levels = [0,1,2,3,4,5,6,7,8,9];
		    $scope.popoverLevel = popover;
		  });

		$scope._isNotMobile = displayService.isNotMobile;
		$scope.coursModel.allErrors = [];
		coursService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.coursModel.loaded = true;
				$scope.coursModel.cours = result.cours;
				$scope.coursModel.isAdmin = result.isOwner;
				$scope.coursModel.owner = result.owner;
				$scope.coursModel.averageRank = result.averageRank;
				$scope.coursModel.rank = result.rank;
				$scope.coursModel.topics = result.topics;
				$scope.coursModel.displayPicture = "img/challenge/default.png";
				$scope.coursModel.title = result.cours.title;
				$scope.coursModel.about = result.cours.about;
				$scope.coursModel.open = ($scope.coursModel.cours.open != 0);
				if(null != result.cours.picture){
					$scope.coursModel.displayPicture = "data:image/jpeg;base64,"+ result.cours.picture;
				}
			}
		});
	};
	
	/**
	 * fonction d'ouverture du menu de choix du type de challenge
	 */
	$scope.openLevelMin = function($event){
		$scope.popoverLevel.show($event);
	}
	
	/**
	 * fonction de changement du level minimum
	 */
	$scope.changeLevelMin = function(level){
		$scope.popoverLevel.hide();
		$scope.coursModel.cours.levelMin = level;
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("level", $scope.coursModel.cours.levelMin);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.level(data);
	}
	
	/**
     * Modal de confirmation de la suppression d'un challenge
     */
     $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
	   });
	
	/**
	 * Modification de l'image du cours [desktop mode]
	 */
	$scope.desktopPicture = function(input){
		var reader = new FileReader();
        reader.onload = function (e) {
        	var data = new FormData();
			data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
			data.append("cours", $scope.coursModel.cours.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			coursService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.coursModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
					$scope.coursModel.displayPicture = e.target.result;
				}
			});
        }
        reader.readAsDataURL(input.files[0]);  
	}
	
	/**
	 * Modification de l'ouverture fermeture d'un cours
	 */
	$scope.toogleOpen = function(){
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.toogleOpen(data);
	}
	
	/**
	 * Modification de l'image du cours [mobile mode]
	 */
	$scope.picture = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {			
			var data = new FormData();
			data.append("file", imageData);
			data.append("cours", $scope.coursModel.cours.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			coursService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.coursModel.file = imageData;
					$scope.coursModel.displayPicture = "data:image/jpeg;base64,"+imageData;
					$scope.coursModel.iconEdit = false;
		    	}
		    });
		 }, function(err) {
		 });
	}

   /**
    * demande de confirmation de la suppression du cours
    */
    $scope.confirmDeleteCours = function(){
	   $scope.deleteType = true;
	   $scope.modal.show();
	}
   
   /**
    * demande de confirmation de la suppression d'un topic
    */
    $scope.confirmDeleteTopic = function(topic){
	   $scope.deleteType = false;
	   $scope.eltToDelete  = topic;
	   $scope.modal.show();
	}
    
    /**
   	 * Suppression définitive d'un cours ou d'un topic
   	 */
   	$scope.confirmDelete = function(){
   		$scope.modal.hide();
   		if($scope.deleteType){
   			deleteCours();
   		} else {
   			deleteTopic();
   		}
   	};
   	
   	/**
	 * Suppression en base d'un cours
	 */
	var deleteCours = function(){
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.deleteById(data).success(function(result) {
			if(result.code == 1){
				$state.go('tab.certificates');
	    	}
	    });
		return;
	}
	
	/**
	 * Suppression en base d'un topic
	 */
	var deleteTopic = function(){
		// TODO 
	}

	
});