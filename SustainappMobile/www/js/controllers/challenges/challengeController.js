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
		$scope.challengeModel.edit = false;
		$scope.challengeModel.iconEdit = false;
		$scope.challengeModel.file  = null;
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
				$scope.challengeModel.teams = result.teams;
				$scope.challengeModel.isAdmin = result.isAdmin;
				$scope.challengeModel.participations = result.participations;
				$scope.challengeModel.displayIcon = "img/challenge/default.png";
				if(null != result.challenge.icon){
					$scope.challengeModel.displayIcon = "data:image/jpeg;base64,"+ result.challenge.icon;
				}
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
		fileService.getFile(newFile, 100, 700, 400).then(function(imageData) {			
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
    * Modal de confirmation de la suppression d'un challenge
    */
   $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
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
	
});