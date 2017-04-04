/**
 * Controller pour l'affichage principal d'un topic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('topicController', 
			function($scope, $stateParams, $state, $ionicModal, sessionService, topicService, fileService, listService, displayService) {
		
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
				loadTopic();
	        });
		
		/**
		 * Chargement des informations du topic
		 */
		var loadTopic = function(){
			$scope.topicModel = {};
			$scope.topicModel.loaded = false;
			$scope.title = "...";
			
			$scope.topicModel.edit = false;
			$scope.topicModel.pictureEdit = false;
			$scope.topicModel.file  = null;
			$scope.topicModel.displayPicture  = "";
			
			$scope.eltToDelete  = {};
			
			$scope.topicModel.topic = {};
			$scope.topicModel.parts = {};
			$scope.topicModel.isOwner = false;
			$scope.topicModel.title = "";
			$scope.topicModel.content = "";
			
			$scope._isNotMobile = displayService.isNotMobile;
			$scope.topicModel.allErrors = [];
			
			topicService.getById($stateParams.id, sessionService.get('id')).then(function(response){
				var result = response.data;
				if(result.code == 1) {
					$scope.topicModel.loaded = true;
					$scope.topicModel.topic = result.topic;
					$scope.topicModel.parts = result.part;
					$scope.topicModel.isOwner = result.isOwner;
					$scope.topicModel.title = result.topic.title;
					$scope.title = result.topic.title;
					$scope.topicModel.content = result.topic.content;
					if(null != result.topic.picture){
						$scope.topicModel.displayPicture = "data:image/jpeg;base64,"+ result.topic.picture;
					}
				}
			});		
		};
	
		/**
	     * Modal de confirmation de la suppression d'une partie
	     */
	     $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
		     scope: $scope
		   }).then(function(modal) {
		     $scope.modal = modal;
		   });
	   
	     /**
	      * Modification de la photo de fon [desktop mode]
	      */
	     $scope.desktopPicture = function(input){
	    	 var reader = new FileReader();
	         reader.onload = function (e) {
	         	var data = new FormData();
	 			data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
	 			data.append("topic", $scope.topicModel.topic.id);
	 			data.append("sessionId", sessionService.get('id'));
	 			data.append("sessionToken", sessionService.get('token'));
	 			topicService.picture(data).success(function(result) {
	 				if(result.code == 1){
	 					$scope.topicModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
	 					$scope.topicModel.displayPicture = e.target.result;
	 				}
	 			});
	         }
	         reader.readAsDataURL(input.files[0]);  
	     };
	     
	     /**
	      * Modification de la photo de fon [mobile mode]
	      */
	     $scope.picture = function(newFile){
	    	 fileService.getFile(newFile, 100, 700, 300, false).then(function(imageData) {			
	 			var data = new FormData();
	 			data.append("file", imageData);
	 			data.append("topic", $scope.topicModel.topic.id);
	 			data.append("sessionId", sessionService.get('id'));
	 			data.append("sessionToken", sessionService.get('token'));
	 			topicService.picture(data).success(function(result) {
	 				if(result.code == 1){
	 					$scope.topicModel.file = imageData;
	 					$scope.topicModel.displayPicture = "data:image/jpeg;base64,"+imageData;
	 					$scope.topicModel.pictureEdit = false;
	 		    	}
	 		    });
	 		 }, function(err) {
	 		 });
	     };
	     
	     /**
	      * Update topic informations
	      */
	     $scope.updateTopic = function(){
	    	var data = new FormData();
 			data.append("title", $scope.topicModel.title);
 			data.append("about", $scope.topicModel.content);
 			data.append("topic", $scope.topicModel.topic.id);
 			data.append("sessionId", sessionService.get('id'));
 			data.append("sessionToken", sessionService.get('token')); 
 			topicService.update(data).success(function(result) {
 				if(result.code == 1){
 					$scope.topicModel.topic.title = $scope.topicModel.title;
 					$scope.topicModel.topic.content = $scope.topicModel.content;
 					$scope.topicModel.allErrors = [];
 					$scope.topicModel.edit = false;
 		    	} else {
 		    		$scope.topicModel.allErrors = result.errors;
 		    	}
 		    });
	     };
	     
});