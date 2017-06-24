/**
 * Controller pour la page d'une seule question
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('questionController', function($scope, $ionicPopover, $state, $stateParams, $ionicModal, questionTypeService, sessionService, answerService, questionService, answerCategoryService, fileService, displayService) {
	
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
		loadQuestion();
    });
	
	/**
	 * Chargement de toutes les informations de la question
	 */
	var loadQuestion = function(){
		$scope.questionModel = {};		
		$scope.eltToDelete  = {};
		$scope.typeToDelete = true;
		
		$scope.questionModel.loaded = false;
		$scope.questionModel.allErrors = [];
		
		$scope.questionModel.reorderCategory = false;
		$scope.questionModel.reorderAnswer = false;
		$scope.questionModel.edit = false;
		$scope.questionModel.displayPicture = ""; 
		$scope.questionModel.picture = null;		
		$scope.questionModel.pictureEdit = false;
		
		$scope.answerModel = {};
		$scope.answerModel.message = "";
		$scope.answerModel.data = "";
		$scope.answerModel.selected = false;
		$scope.answerModel.displayPicture = ""; 
		$scope.answerModel.picture = null;		
		$scope.answerModel.pictureEdit = false;
		$scope.answerModel.emptyFile = true;
		$scope.answerModel.allErrors = [];
		
		$scope.categorieModel = {};
		$scope.categorieModel.message = "";
		$scope.answerModel.allErrors = [];
		
		$scope._isNotMobile = displayService.isNotMobile;
		
		questionService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.questionModel.loaded = true;
				$scope.questionModel.cours = result.courseId;
				$scope.questionModel.question = result.question;
				$scope.questionModel.message = result.question.message;
				if(null != result.question.picture){
					$scope.questionModel.displayPicture = "data:image/jpeg;base64,"+result.question.picture; 
					$scope.questionModel.picture =  result.question.picture;
				}
				$scope.questionModel.answers = result.answers;
				$scope.questionModel.categories = result.categories;
				$scope.questionModel.maxCategories = 3;
				$scope.questionModel.maxAnswer = (result.question.type == 0 || result.question.type == 2)? 5 : 4;
			}
		}, function(response){
			sessionService.refresh(loadQuestion);
		});
	}

	/**
	 * Modifier l'image d'une question [mobile mode]
	 */
	$scope.chooseFile = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {			
			var data = new FormData();
			data.append("file", imageData);
			data.append("question", $stateParams.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			questionService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.questionModel.displayPicture = "data:image/jpeg;base64,"+imageData;
					$scope.questionModel.picture = imageData;		
					$scope.questionModel.pictureEdit = false;
		    	}
		    }).error(function(error){
		    	sessionService.refresh(null);
		    }); 
		 }, function(err) {
		 });
	};

	/**
	 * Modifier l'image d'une question [desktop mode]
	 */
	$scope.desktopFile = function(input){
		var reader = new FileReader();
        reader.onload = function (e) {
        	var data = new FormData();
			data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
			data.append("question", $stateParams.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			questionService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.questionModel.displayPicture = e.target.result;
					$scope.questionModel.picture = e.target.result.substring(e.target.result.indexOf(",")+1);	
					$scope.questionModel.pictureEdit = false;
				}
			}).error(function(error){
		    	sessionService.refresh(null);
		    });
        }
        reader.readAsDataURL(input.files[0]);  
	};

	/**
	 * Modifier les informations d'une question
	 */
	$scope.updateQuestion = function(){
		var data = new FormData();
		data.append("message", $scope.questionModel.message);
		data.append("question", $stateParams.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		questionService.update(data).success(function(result) {
			if(result.code == 1){
				$scope.questionModel.question.message = $scope.questionModel.message;
				$scope.questionModel.edit = false;
			}
		}).error(function(error){
	    	sessionService.refresh($scope.updateQuestion);
	    });
	};

	/**
     * Modal de confirmation de la suppression d'une réponse ou catégory
     */
     $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
	   });

     /**
      * Modal d'ajout d'une réponse
      */
      $ionicModal.fromTemplateUrl('templates/certificates/modalAnswer.html', {
 	     scope: $scope
 	   }).then(function(modal) {
 	     $scope.modalAnswer = modal;
 	   });

      /**
       * Modal d'ajout d'une catégorie
       */
       $ionicModal.fromTemplateUrl('templates/certificates/modalCategory.html', {
  	     scope: $scope
  	   }).then(function(modal) {
  	     $scope.modalCategory = modal;
  	   });

	/**
	 * Popover pour le choix d'une catégorie
	 */
	$ionicPopover.fromTemplateUrl('templates/certificates/popover-category.html', {
	    scope: $scope
	  }).then(function(popover) {
	    $scope.popoverCategory = popover;
	  });

	/**
	 * Ouverture de la modale de supression avec choix du type
	 */
	$scope.openDeleteModal = function(type, elt){
		$scope.eltToDelete  = elt;
		$scope.typeToDelete = type;
		$scope.modal.show();
	};

	/**
	 * Validation de la suppression et redirection
	 */
	$scope.confirmDelete = function(type){
		$scope.modal.hide();
		if(true == $scope.typeToDelete){
			$scope.deleteCategory();
		}else{
			$scope.deleteAnswer();
		}
	};
	
	/**
	 * Ajout d'une catégorie
	 */
	$scope.addCategory = function(){
		var data = new FormData();
		data.append("question", $stateParams.id);
		data.append("message", $scope.categorieModel.message);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		answerCategoryService.create(data).success(function(result) {
			if(result.code == 1){
				var categorie = {
						"id"    : result.id,
						"name" : $scope.categorieModel.message,						
				};
				$scope.questionModel.categories.push(categorie);
				$scope.categorieModel.message = "";				
				$scope.categorieModel.allErrors = [];
				$scope.modalCategory.hide();
	    	} else {
	    		$scope.categorieModel.allErrors = result.errors;
	    	}
	    }).error(function(error){
	    	sessionService.refresh($scope.addCategory);
	    });
	};
	
	/**
	 * Suppression d'une catégorie
	 */
	$scope.deleteCategory = function(){	
		var data = new FormData();
		data.append("categorie", $scope.eltToDelete.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		answerCategoryService.deleteById(data).success(function(response){
			$scope.questionModel.categories.splice($scope.questionModel.categories.indexOf($scope.eltToDelete), 1);
		}).error(function(error){
	    	sessionService.refresh($scope.deleteCategory);
	    });
		$scope.eltToDelete  = {};
	};
	
	/**
	 * Déplacement d'une catégorie
	 */
	$scope.dropCategory = function(elt, fromIndex, toIndex){	
		var data = new FormData();
		data.append("categorie", elt.id);
		data.append("position", toIndex);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		answerCategoryService.drop(data).success(function(response){
			$scope.questionModel.categories.splice(fromIndex, 1);
			$scope.questionModel.categories.splice(toIndex, 0, elt);
		}).error(function(error){
	    	sessionService.refresh(null);
	    });
	};
	
	/**
	 * Ajout d'une réponse
	 */
	$scope.addAnswer = function(){
		var data = new FormData();
		data.append("question", $stateParams.id);
		data.append("message", $scope.answerModel.message);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		if(null != $scope.answerModel.picture && ($scope.questionModel.question.type == 1 || $scope.questionModel.question.type == 3)) {
			data.append("file", $scope.answerModel.picture);
		}
		if($scope.questionModel.question.type == 1 || $scope.questionModel.question.type == 0){
			$scope.answerModel.data = $scope.answerModel.selected;
		}
		data.append("data", $scope.answerModel.data);
		answerService.create(data).success(function(result) {
			if(result.code == 1){
				var answer = {
						"id"    : result.id,
						"message" : $scope.answerModel.message,
						"picture" : $scope.answerModel.picture,
						"data" : $scope.answerModel.data,
				};
				$scope.questionModel.answers.push(answer);
				$scope.answerModel.allErrors = [];
				$scope.modalAnswer.hide();
				$scope.answerModel.message = "";
				$scope.answerModel.data = "";
				$scope.answerModel.selected = false;
				$scope.answerModel.displayPicture = ""; 
				$scope.answerModel.picture = null;		
				$scope.answerModel.pictureEdit = false;
				$scope.answerModel.emptyFile = true;				
	    	} else {
	    		$scope.answerModel.allErrors = result.errors;
	    	}
	    }).error(function(error){
	    	sessionService.refresh($scope.addAnswer);
	    });
	};
	
	/**
	 * Suppression d'une réponse
	 */
	$scope.deleteAnswer = function(){		
		var data = new FormData();
		data.append("answer", $scope.eltToDelete.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		answerService.deleteById(data).success(function(response){
			$scope.questionModel.answers.splice($scope.questionModel.answers.indexOf($scope.eltToDelete), 1);
		}).error(function(error){
	    	sessionService.refresh($scope.deleteAnswer);
	    });
		$scope.eltToDelete  = {};
	};
	
	/**
	 * Déplacement d'une réponse
	 */
	$scope.dropAnswer = function(elt, fromIndex, toIndex){	
		var data = new FormData();
		data.append("answer", elt.id);
		data.append("position", toIndex);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		answerService.drop(data).success(function(response){
			$scope.questionModel.answers.splice(fromIndex, 1);
			$scope.questionModel.answers.splice(toIndex, 0, elt);
		}).error(function(error){
	    	sessionService.refresh(null);
	    });
	};
	
	/**
	 * Choix d'une image pour une réponse [mobile mode]
	 */
	$scope.chooseAnswerFile = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
			$scope.answerModel.displayPicture = "data:image/jpeg;base64,"+imageData; 
			$scope.answerModel.picture = imageData;		
			$scope.answerModel.pictureEdit = false;
			$scope.answerModel.emptyFile = false;
		 }, function(err) {
		 });
	};

	/**
	 * Choix d'une image pour une réponse [desktop mode]
	 */
	$scope.desktopAnswerFile = function(input){
		var reader = new FileReader();
        reader.onload = function (e) {
        	$scope.$apply(function () {       		
        		$scope.answerModel.emptyFile = false;       		
        		$scope.answerModel.displayPicture = e.target.result;
        		$scope.answerModel.picture = e.target.result.substring(e.target.result.indexOf(",")+1);		
        		$scope.answerModel.pictureEdit = false;
            });           	         	
        }
        reader.readAsDataURL(input.files[0]);
	};

	/**
	 * Ouvrir la modale de création d'une nouvelle réponse
	 */
	$scope.openAnswerModal = function(){
		if(2 == $scope.questionModel.question.type && 0 < $scope.questionModel.categories.length){
			$scope.answerModel.data = $scope.questionModel.categories[0].name;
		}
		$scope.modalAnswer.show();
	}
	
	/**
	 * Ouverture du choix d'une catégorie
	 */
	$scope.openCategories = function($event){
		 $scope.popoverCategory.show($event);
	}
	
	/**
	 * Confirmation du choix d'une catégorie
	 */
	$scope.changeCategory = function(elt){
		$scope.answerModel.data = elt.name;
		$scope.popoverCategory.hide();
	}
	
	/**
 	  * fix freeze screen
 	  */
 	 $scope.$on('modal.hidden', function() {
 		 $scope.popoverCategory.hide();
 	 }); 

});