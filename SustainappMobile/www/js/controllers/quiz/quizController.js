/**
 * Controller for quiz validation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('quizController',
		function($scope, $stateParams, $ionicModal, $state, sessionService, quizService, displayService) {

		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadQuiz();
		});
		
		/**
		 * Chargement de toutes les informations du quiz
		 */
		var loadQuiz = function(){
			
			// Le quiz
			$scope.quizModel = {};
			$scope.quizModel.loaded = false;
			$scope.quizModel.questions = [];
			$scope.quizModel.responses = [];
			$scope.quizModel.topic = $stateParams.id;
			$scope.currentPosition = 0;
			$scope.currentQuestion = {};							
			$scope.btnTxt = "quiz.next";
			
			// la validation du quiz
			$scope.reponseModel = {};
			$scope.reponseModel.loaded = false;
			
			quizService.getById($stateParams.id).then(function(response){
				var result = response.data;
				if(result.code == 1) {		
					$scope.quizModel.questions = result.questions;
					$scope.currentQuestion = result.questions[0];
					$scope.progressBarSize  = Math.floor(100/result.questions.length);
					$scope.quizModel.loaded = true;					
				}
			});
		};
		
		/**
		 * Fonction d'activation/desactivation du scrolling
		 */
		$scope.enableScroll = function(value){
			displayService.enableScroll("quizContent", value);
		};
		
		/**
		 * Validation d'une question et passage à la suivante
		 */
		$scope.validateAnswer = function(currentQuestion){
			var newAnswer = "";
			angular.forEach(currentQuestion.answers, function(answer, key) {
				newAnswer+=answer.data+"/";
			});
			$scope.quizModel.responses.push(newAnswer);
			if(null != $scope.quizModel.questions[$scope.currentPosition + 1]){				
				if(null == $scope.quizModel.questions[$scope.currentPosition + 2]){
					$scope.btnTxt = "quiz.validate";
				}
				$scope.currentPosition = $scope.currentPosition + 1;
				$scope.currentQuestion = $scope.quizModel.questions[$scope.currentPosition];				
			}else{
				var data = new FormData();
				data.append("quiz", $stateParams.id);
				data.append("answers", $scope.quizModel.responses);
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				quizService.validateQuiz(data).success(function(result) {
					if(result.code == 1){
						
					}
				});
			}
		};
		
		/**
		 * Drag&Drop pour les questions de type classification
		 */
		$scope.onClassificationComplete = function(answer, event, cat){
			answer.data = cat.id;
		};
		
		/**
		 * Drag&Drop pour les questions de type reorder
		 */
		$scope.onReorderComplete = function(answer, event, number){
			answer.data = number;
		}
});