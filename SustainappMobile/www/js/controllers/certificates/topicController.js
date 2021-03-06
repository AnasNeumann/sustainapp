/**
 * Controller pour l'affichage principal d'un topic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('topicController', 
			function($scope, $stateParams, $state, $rootScope, $ionicModal, $ionicScrollDelegate, $cordovaInAppBrowser, sessionService, topicService, fileService, listService, partService, displayService) {
		
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
			$scope.topicModel.parts = [];
			
			$scope._isNotMobile = displayService.isNotMobile;
			$scope.topicModel.allErrors = [];
			
			$scope.topicModel.blueMenu = false;
			
			$scope.partModel = {};
			$scope.partModel.title = "";
			$scope.partModel.link = "";
			$scope.partModel.content = "";
			$scope.partModel.video = "";
			$scope.partModel.pictureEdit = false;
			$scope.partModel.file  = null;
			$scope.partModel.displayPicture  = "";
			$scope.partModel.emptyPicture  = true;
			$scope.partModel.type = 1;
			$scope.partModel.allErrors = [];
			$scope.partModel.eltToDelete = {};
			$scope.partModel.eltToEdit = {};
			$scope.partModel.contentEdit = "";
	    	$scope.partModel.titleEdit = "";
	    	
	    	topicService.getById($stateParams.id, sessionService.get('id')).then(function(response){
				var result = response.data;
				if(result.code == 1) {
					$scope.topicModel.loaded = true;
					$scope.topicModel.topic = result.topic;
					$scope.topicModel.parts = result.parts;
					$scope.topicModel.isOwner = false;
					$scope.topicModel.isReallyOwner = result.isOwner;
					$scope.topicModel.title = result.topic.title;
					$rootScope.$broadcast('TITLE', result.topic.title);
					$scope.topicModel.hasQuiz = result.hasQuiz;
					$scope.topicModel.content = result.topic.content;
					if(null != result.topic.picture){
						$scope.topicModel.displayPicture = "data:image/jpeg;base64,"+ result.topic.picture;
					}
				}
			}, function(response){
				sessionService.refresh(loadTopic); 
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
	      * Modification de la photo de fond [desktop mode]
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
	 			}).error(function(error){
			    	sessionService.refresh(null);
			    }); 
	         }
	         reader.readAsDataURL(input.files[0]);  
	     };
	     
	     /**
	      * Modification de la photo de fond [mobile mode]
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
	 		    }).error(function(error){
			    	sessionService.refresh(null);
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
 		    }).error(function(error){
		    	sessionService.refresh($scope.updateTopic);
		    }); 
	     };
	     
	     
	     /**
	      * Modal de confirmation de la suppression d'une partie
	      */
	     $ionicModal.fromTemplateUrl('templates/certificates/modalPart.html', {
		     scope: $scope
		   }).then(function(modal) {
		     $scope.modalPart = modal;
		   });
	     
	    /**
	     * Fonction d'ouverture de la modal d'ajout d'une part
	     */
	     $scope.openPart = function(type){
	    	 $scope.partModel.type = type;
	    	 $scope.modalPart.show();
	     };
	     
	     /**
	      * Fonction de choix de l'image à ajouter [mobile mode]
	      */
	     $scope.choosePartFile = function(newFile){
	    	 fileService.getFile(newFile, 100, 700, 300, false).then(function(imageData) {	 			
	 			$scope.partModel.pictureEdit = false;
				$scope.partModel.file  = imageData;
				$scope.partModel.displayPicture  = "data:image/jpeg;base64,"+imageData;
				$scope.partModel.emptyPicture  = false;
	 		 }, function(err) {
	 		 });
	     };
	     
	     /**
	      * Fonction de choix de l'image à ajouter [desktop mode]
	      */
	     $scope.desktopPartFile = function(input){
	    	 var reader = new FileReader();
	         reader.onload = function (e) {
	         	$scope.$apply(function () {
	         		$scope.partModel.pictureEdit = false;
					$scope.partModel.file  = e.target.result.substring(e.target.result.indexOf(",")+1);
					$scope.partModel.displayPicture  = e.target.result;
					$scope.partModel.emptyPicture  = false;
	             });
	         }
	         reader.readAsDataURL(input.files[0]); 
	     };
	     
	     /**
	      * Fonction d'ajout d'une nouvelle partie au chapitre
	      */
	      $scope.createPart = function(){
	    	  var data = new FormData();
	    	  switch($scope.partModel.type) {
	    	    case 1:
	    	    	data.append("title", $scope.partModel.title);
	    	    	data.append("content", $scope.partModel.content);
	    	        break;
	    	    case 2:
	    	    	data.append("title", $scope.partModel.title);
	    	    	if(null != $scope.partModel.file){
	    	    		data.append("file", $scope.partModel.file);
	    	    	}
	    	        break;
	    	    case 3:
	    	    	data.append("title", $scope.partModel.title);
	    	    	data.append("video", $scope.partModel.video);
	    	        break;
	    	    case 4:
	    	    	data.append("link", $scope.partModel.link);
	    	        break;
		    	}
	 			data.append("topic", $scope.topicModel.topic.id);
	 			data.append("type", $scope.partModel.type);
	 			data.append("sessionId", sessionService.get('id'));
	 			data.append("sessionToken", sessionService.get('token'));
	 			partService.create(data).success(function(result) {
	 				if(result.code == 1){
	 					$scope.modalPart.hide();
	 					$scope.partModel.allErrors = [];
	 					var newPart = {
							"id"    : result.id,
							"type"  : $scope.partModel.type
	 					};
	 					switch($scope.partModel.type) {
		 		    	    case 1:
		 		    	    	newPart.title =  $scope.partModel.title;
		 		    	    	newPart.content =  $scope.partModel.content;
		 		    	        break;
		 		    	    case 2:
		 		    	    	newPart.title =  $scope.partModel.title;
		 		    	    	newPart.document =  $scope.partModel.file;		 		    	    	
		 		    	        break;
		 		    	    case 3:
		 		    	    	newPart.title =  $scope.partModel.title;
		 		    	    	newPart.content =  result.content;
		 		    	        break;
		 		    	    case 4:
		 		    	    	newPart.content =  result.content;
		 		    	        break;
	 			    	}
	 					$scope.topicModel.parts.push(newPart);
	 					$scope.partModel.title = "";
	 					$scope.partModel.link = "";
	 					$scope.partModel.content = "";
	 					$scope.partModel.video = "";
	 					$scope.partModel.pictureEdit = false;
	 					$scope.partModel.file  = null;
	 					$scope.partModel.displayPicture  = "";
	 					$scope.partModel.emptyPicture  = true;	 					
	 					var scrollDiv = document.getElementById("topicContent");	 					
	 					displayService.animatedScrollDown(scrollDiv, scrollDiv.scrollHeight, 1, 20);	 					
	 				} else {
	 		    		$scope.partModel.allErrors = result.errors;
	 		    	}
	 		    }).error(function(error){
			    	sessionService.refresh($scope.createPart);
			    });
	      };
	      
	      /**
	       * Fonction de scroll vers le haut
	       */
	      $scope.scrollTop = function(){
	    	  var scrollDiv = document.getElementById("topicContent");	 					
			  displayService.animatedScrollUp(scrollDiv, 0, scrollDiv.scrollHeight, 25); 
	      };
	      
	      /**
	       * Fonction de demande de confirmation de la suppression
	       */
	      $scope.deletePart = function(elt){
	    	  $scope.partModel.eltToDelete = elt;
	    	  $scope.modal.show();
	      };
	      
	      /**
	       * Confirmation de la suppression d'une partie
	       */
	      $scope.confirmDelete = function(){
	    	$scope.modal.hide();
	    	$scope.topicModel.parts.splice($scope.topicModel.parts.indexOf($scope.partModel.eltToDelete), 1);
	  		var data = new FormData();
	  		data.append("part", $scope.partModel.eltToDelete.id);
	  		data.append("sessionId", sessionService.get('id'));
	  		data.append("sessionToken", sessionService.get('token'));
	  		partService.deleteById(data).error(function(error){
		    	sessionService.refresh($scope.confirmDelete);
		    });
	      };
	      
	      /**
	       * Fonction de déplacement vers le haut/bas d'un elt
	       */
	      $scope.movePart = function(elt, sens){
	    	 var fromIndex = $scope.topicModel.parts.indexOf(elt);
	    	 var toIndex = fromIndex + 1;
	    	 if(true == sens){
	    		 var toIndex = fromIndex - 1;
	    	 }	    	 
	 		 var data = new FormData();
	  		 data.append("part", elt.id);
	  		 data.append("sens", sens);
	  		 data.append("sessionId", sessionService.get('id'));
	  		 data.append("sessionToken", sessionService.get('token'));
	  		 partService.move(data).success(function(result) {
	  			$scope.topicModel.parts.splice(fromIndex, 1);
		    	$scope.topicModel.parts.splice(toIndex, 0, elt);
	  		 }).error(function(error){
			    	sessionService.refresh(null);
			 });
	      };
	      
	      /**
	       * Fonction d'ouverture d'un onglet externe
	       */
	      $scope.openLink = function(link){
	    	  if(!$scope._isNotMobile){
	    		  var options = {
	    			      location: 'yes',
	    			      clearcache: 'yes',
	    			      toolbar: 'no'
	    			    };
	    		  $cordovaInAppBrowser.open(link, '_system', options); 
	    	  }else{
	    		  window.open(link, '_system', 'location=yes'); 
	    	  }	    	  
	    	  return false;
	      };
	      
	      /**
	       * Fonction d'ouverture de la modification 
	       */
	      $scope.openModif = function(elt){
	    	  $scope.partModel.eltToEdit = elt;
	    	  $scope.partModel.contentEdit = elt.content;
	    	  $scope.partModel.titleEdit = elt.title;
	      }
	      
	      /**
	       * Fonction de fermeture de la modification
	       */
	      $scope.closeModif = function(elt){
	    	  $scope.partModel.eltToEdit = {};
	      };
	      
	      /**
	       * Fonction de modification en base d'une partie
	       */
	      $scope.modifyPart = function(){
	    	  	var data = new FormData();
	 			data.append("part", $scope.partModel.eltToEdit.id);
	 			data.append("content",  $scope.partModel.contentEdit);
	 			data.append("title", $scope.partModel.titleEdit);
	 			data.append("sessionId", sessionService.get('id'));
	 			data.append("sessionToken", sessionService.get('token'));
	 			partService.update(data).success(function(result) {
	 				if(result.code == 1){
	 					$scope.partModel.eltToEdit.content = $scope.partModel.contentEdit;
	 					$scope.partModel.eltToEdit.title = $scope.partModel.titleEdit;
	 					$scope.partModel.eltToEdit = {};
	 				}
	 			}).error(function(error){
			    	sessionService.refresh($scope.modifyPart);
	 			});
	      };
	      
	      /**
	       * Fonction pour aller vers l'interface d'édition d'un quiz
	       */
	      $scope.editQuiz = function(){
	    	  $state.go('tab.questions', { id : $stateParams.id});
	      };
	      
	      /**
	       * Fonction pour aller vers l'interface pour valider le quiz
	       */
	      $scope.goToQuiz = function(){
	    	  $state.go('tab.quiz', { id : $stateParams.id});
	      };
});