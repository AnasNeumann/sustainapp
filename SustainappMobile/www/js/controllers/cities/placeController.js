/**
 * Controller pour l'affichage d'un éco-lieu
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('placeController', 
		function($scope, $stateParams, $ionicModal, sessionService, displayService, placeService) {

		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadPlace();
		});
		
		/**
		 * Chargement complet des informations du lieu
		 */
		var loadPlace = function(){
			$scope.model = {};
			$scope.model.loaded = false;
			$scope.title = "";
			$scope.model.allErrors = [];
			$scope.model.pictures = [];
			placeService.getById($stateParams.id, sessionService.get('id')).then(function(response){
				result = response.data;
				if(result.code == 1) {
					$scope.model.loaded = true;
					$scope.model.average = result.average;
					$scope.model.pictures = result.pictures;
					$scope.model.place = result.place;
					$scope.model.name = result.place.name;
					$scope.model.about = result.place.about;
					$scope.model.address = result.place.address;
					$scope.model.isOwner = result.isOwner;
					$scope.model.currentNote = result.note;
					$scope.model.edit = false;
				}
			});
		};
	
});