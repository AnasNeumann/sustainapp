/**
 * Controller pour la gestion des villes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('citiesController', function($scope, sessionService, listService, cityService) {

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
			cityService.getAll(sessionService.get('id')).then(function(response){
				result = response.data;
				if(result.code == 1) {		
					$scope.model.cities = result.cities;					
				}
			});
		};
		
});