/**
 * Controller pour l'affichage d'un challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('challengeController', function($scope, $stateParams, $ionicModal, $state, $ionicPopover, sessionService, fileService, challengeService, participationService) {

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
		
		$scope.challengeModel.emptyFile = true;
		$scope.challengeModel.edit = false;
		$scope.challengeModel.iconEdit = false;
		$scope.challengeModel.file  = null;
		
		$scope.challengeModel.emptyParticipationFile = true;
		$scope.challengeModel.editParticipationFile = false;
		$scope.challengeModel.participationFile = null;
		$scope.challengeModel.displayParticipationFile = null;
		$scope.challengeModel.selectedProfile = {};
		$scope.challengeModel.allProfiles = [];
		
		$scope.challengeModel.allErrors = [];	
		challengeService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.challengeModel.loaded = true;
				$scope.title = result.challenge.name;
				$scope.challengeModel.challenge = result.challenge;
				$scope.challengeModel.name = result.challenge.name;
				$scope.challengeModel.about = result.challenge.about;
				$scope.challengeModel.owner = result.owner;						
				$scope.challengeModel.allProfiles = result.lightProfiles;
				$scope.challengeModel.selectedProfile = result.lightProfiles[0];
				$scope.challengeModel.isOpen = result.isOpen;
				$scope.challengeModel.isAdmin = result.isAdmin;
				$scope.challengeModel.participations = result.participations;
				$scope.challengeModel.displayIcon = "img/challenge/default.png";
				if(null != result.challenge.icon){
					$scope.challengeModel.displayIcon = "data:image/jpeg;base64,"+ result.challenge.icon;
				}
				$ionicPopover.fromTemplateUrl('templates/challenges/popover-profiles.html', {
				    scope: $scope
				  }).then(function(popover) {
				    $scope.popoverProfiles = popover;
				  });
			}
		});
	};
	
	/**
	 * Modification des informations du challenge
	 */
	$scope.updateChallenge = function(){
		var data = new FormData();
		data.append("name", $scope.challengeModel.name);
		data.append("about", $scope.challengeModel.about);
		data.append("challenge", $scope.challengeModel.challenge.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		challengeService.update(data).success(function(result) {
			if(result.code == 1){
				$scope.challengeModel.allErrors = [];	
				$scope.challengeModel.challenge.name = $scope.challengeModel.name;
				$scope.challengeModel.challenge.about = $scope.challengeModel.about;
				$scope.challengeModel.edit = false;
				$scope.challengeModel.iconEdit = false;
	    	} else {
	    		$scope.challengeModel.allErrors = result.errors;
	    	}
	    });
	}
	
	/**
	 * Modification de l'icon d'un challenge
	 */
	$scope.icon = function(newFile){
		fileService.getFile(newFile, 100, 600, 600).then(function(imageData) {			
			var data = new FormData();
			data.append("file", imageData);
			data.append("challenge", $scope.challengeModel.challenge.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			challengeService.icon(data).success(function(result) {
				if(result.code == 1){
					$scope.challengeModel.file = imageData;
					$scope.challengeModel.displayIcon = "data:image/jpeg;base64,"+imageData;
					$scope.challengeModel.iconEdit = false;
		    	}
		    });
		 }, function(err) {
		 });
	};
	
	/**
	 * Modification de l'image d'une nouvelle participation
	 */
	$scope.chooseParticipationFile = function(newFile){
		fileService.getFile(newFile, 100, 700, 300).then(function(imageData) {			
			$scope.challengeModel.participationFile = imageData;
			$scope.challengeModel.displayParticipationFile = "data:image/jpeg;base64,"+imageData;
			$scope.challengeModel.emptyParticipationFile = false;
			$scope.challengeModel.editParticipationFile = false;
		 }, function(err) {
		 });
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
    * Modal de particiaption
    */
   $ionicModal.fromTemplateUrl('templates/challenges/participateModal.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.participateModal = modal;
	   });
   
   
    /**
	 * Suppression définitive d'un chalenge
	 */
	$scope.confirmDelete = function(){
		$scope.modal.hide();
		var data = new FormData();
		data.append("challenge", $scope.challengeModel.challenge.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		challengeService.deleteById(data).success(function(result) {
			if(result.code == 1){
				$state.go('tab.challenges');
	    	}
	    });
		return;
	};
	
	/**
	 * fonction d'ouverture du menu de choix du profile ou de la team
	 */
	$scope.openProfiles = function($event){
		$scope.popoverProfiles.show($event);
	}
	
	/**
	 * fonction de changement du choix du profile ou de la team
	 */
	$scope.changeProfiles = function(profile){
		$scope.popoverProfiles.hide();
		$scope.challengeModel.selectedProfile = profile
	}
	
	/**
	 * fix freeze screen
	 */
	$scope.$on('modal.hidden', function() {
		$scope.popoverProfiles.hide();
	});
	
	/**
	 * Ajout d'une participation
	 */
	$scope.participate = function(){
		
	}
	
	/**
	 * Ajout d'un vote
	 */
	$scope.vote = function(participation){
		
	}
	
	/**
	 * Modification d'une participation
	 */
	$scope.updateParticipation = function(){
		
	}
	
	/**
	 * Suppression d'une participation
	 */
	$scope.deleteParticipation = function(participation){
		
	}
	
	/**
	 * Visualisation de tout les votes d'une participation
	 */
	$scope.getVotes = function(participation){
		
	}
	
});