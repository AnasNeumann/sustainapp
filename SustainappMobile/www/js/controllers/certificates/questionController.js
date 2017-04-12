/**
 * Controller pour la page d'une seule question
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('questionController', function($scope, $ionicPopover, $state, $stateParams, $ionicModal, questionTypeService, sessionService, questionService, fileService, displayService) {
	
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
		});
	};
	
});