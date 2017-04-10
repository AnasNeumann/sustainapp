/**
 * Controller pour la page des questions
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('questionsController', function($scope, $ionicPopover, $state, $stateParams, $ionicModal, questionTypeService, sessionService, questionService, fileService, displayService) {
  
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
		loadQuestions();
    });
	
	/**
	 * Chargement de toutes les questions
	 */
	var loadQuestions = function(){
		$scope.questionsModel = {};		
		$scope.title = "...";
		$scope.eltToDelete  = {};
		
		$scope.questionsModel.loaded = false;
		$scope.questionsModel.allErrors = [];
		$scope.questionsModel.topic = $stateParams.id;
		$scope.questionsModel.cours = null;
		$scope.questionsModel.message = "";
		$scope.questionsModel.types = questionTypeService.getAll();
		$scope.questionsModel.type = $scope.questionsModel.types[0];
		$scope.questionsModel.hasPicture = false;
		$scope.questionsModel.questions = [];
		
		$scope.questionsModel.emptyPicture = true;
		$scope.questionsModel.pictureEdit = false;
		$scope.questionsModel.file  = null;
		$scope.questionsModel.displayPicture  = "";
		$scope.questionsModel.reorder = false;
		
		$scope._isNotMobile = displayService.isNotMobile;
		
		questionService.getAll($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {				
				$scope.questionsModel.loaded = true;
				$scope.questionsModel.cours = result.courseId;
				$scope.questionsModel.questions = result.questions;				
			}
		});
	};
	
	/**
	 * Popover pour le choix d'un type de question
	 */
	$ionicPopover.fromTemplateUrl('templates/certificates/popover-types.html', {
	    scope: $scope
	  }).then(function(popover) {
	    $scope.popoverType = popover;
	  });
	
	/**
     * Modal de confirmation de la suppression d'une question
     */
     $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
	   });
     
     /**
      * Modal d'ajout d'une nouvelle question
      */
      $ionicModal.fromTemplateUrl('templates/certificates/modalQuestion.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modalQuestion = modal;
	   });
      
      /**
       * Fonction de choix du type de question
       */
      $scope.changeQuestionType = function(type){
    	  $scope.questionsModel.type = type;
    	  $scope.popoverType.hide();
      };
      
      /**
       * Fonction d'ouveture de la liste de choix du type
       */
      $scope.openTypes = function($event){
    	  $scope.popoverType.show($event);
      };
      
  	 /**
  	  * fix freeze screen
  	  */
  	 $scope.$on('modal.hidden', function() {
  		 $scope.popoverType.hide();
  	 });
  	 
 	/**
 	 * Modification de l'image pour la nouvelle question [mobile mode]
 	 */
 	$scope.chooseFile = function(newFile){
 		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
 			$scope.questionsModel.emptyPicture = false;
 			$scope.questionsModel.pictureEdit = false;
 			$scope.questionsModel.file  = imageData;
 			$scope.questionsModel.displayPicture  = "data:image/jpeg;base64,"+imageData;
 			
 		 }, function(err) {
 		 });
 	};
 	
 	/**
 	 * Modification de l'image pour la nouvelle question [desktop mode]
 	 */
 	$scope.desktopFile = function(input){
 		var reader = new FileReader();
         reader.onload = function (e) {
         	$scope.$apply(function () {
         		$scope.questionsModel.emptyPicture = false;
     			$scope.questionsModel.pictureEdit = false;
     			$scope.questionsModel.file  = e.target.result.substring(e.target.result.indexOf(",")+1);
     			$scope.questionsModel.displayPicture  =  e.target.result; 
             });	
         }
         reader.readAsDataURL(input.files[0]);  
 	}
 	
 	/**
 	 * Fonction d'ajout en base d'une nouvelle question
 	 */
 	$scope.addQuestion = function(){
 		var data = new FormData();
		data.append("topic", $scope.questionsModel.topic);
		data.append("message", $scope.questionsModel.message);
		data.append("type", $scope.questionsModel.type.code);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		if(null != $scope.questionsModel.file) {
			data.append("file", $scope.questionsModel.file);
		}
		questionService.create(data).success(function(result) {
			if(result.code == 1){								
				var question = {
						"type" : $scope.questionsModel.type.code,
						"message" : $scope.questionsModel.message,
						"id" : result.id
				};
				$scope.questionsModel.questions.push(question);
				$scope.questionsModel.message = "";
				$scope.questionsModel.emptyPicture = true;
     			$scope.questionsModel.pictureEdit = false;
     			$scope.questionsModel.file = null;
     			$scope.questionsModel.displayPicture  = ""; 
				$scope.questionsModel.allErrors = [];
				$scope.modalQuestion.hide();
	    	} else {
	    		$scope.questionsModel.allErrors = result.errors;
	    	}
	    });
 	};
 	
 	/**
 	 * Fonction de demande de confirmation avant la suppression
 	 */
 	$scope.confirmDeleteQuestion = function(question){
 		$scope.eltToDelete = question;
 		$scope.modal.show();
 	}
 	
 	/**
 	 * Fonction de validation de la suppression
 	 */
	$scope.confirmDelete = function(){		
		$scope.modal.hide();
		$scope.questionsModel.questions.splice($scope.questionsModel.questions.indexOf($scope.eltToDelete), 1);
		var data = new FormData();
		data.append("question", $scope.eltToDelete.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		questionService.deleteById(data);
		$scope.eltToDelete = {};
	}
});