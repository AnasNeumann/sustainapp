/**
 * Controller pour la gestion des villes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('citiesController', function($scope, $ionicModal, sessionService, cityService) {

		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadCities();
        });
		
		/**
		 * Chargement de toutes les villes non validées
		 */
		var loadCities = function(){
			$scope.model = {};
			$scope.model.cities = [];
			$scope.model.eltToDelete = {};
			cityService.getAll(sessionService.get('id')).then(function(response){
				result = response.data;
				if(result.code == 1) {		
					$scope.model.cities = result.cities;					
				}
			}, function(response){
				sessionService.refresh(loadCities);
			});
		};

		/**
		 * Accepter une ville comme étant réelle
		 */
		$scope.accept = function(city){		
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			data.append("city", city.id);
			cityService.validate(data).success(function(response){
				$scope.model.cities.splice($scope.model.cities.indexOf(city), 1);
			}).error(function(error){
		    	sessionService.refresh(null);
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
		 * Refuser une ville et donc supprimer
		 */
		$scope.refuse = function(city){
			$scope.model.eltToDelete = city;
			$scope.modal.show();
		};	
		
		/**
		 * Confirmation de la suppression
		 */
		$scope.confirmDelete = function(){			
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			data.append("city", $scope.model.eltToDelete.id);
			cityService.deleteById(data).success(function(response){
				$scope.model.cities.splice($scope.model.cities.indexOf($scope.model.eltToDelete), 1);
				$scope.modal.hide();
			}).error(function(error){
		    	sessionService.refresh($scope.confirmDelete);
		    });
		};
});