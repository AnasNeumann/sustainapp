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
	
});