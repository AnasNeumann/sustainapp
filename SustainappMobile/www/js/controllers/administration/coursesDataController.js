/**
 * Controller de la visualisation des données
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('coursesDataController', function($scope, sessionService, administrationService) {
	
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadModel();
        });
		
		/**
		 * Chargement du model
		 */
		var loadModel  = function(){
			$scope.model = {};
			$scope.model.loaded = false;
			$scope.model.mostSeen = [];
			$scope.model.categoriesData = [];
			$scope.model.categoriesLabels = [];
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			administrationService.courses(data).success(function(result) {
				$scope.model.loaded = true;
				$scope.model.total = result.total;
				$scope.model.mostSeen = result.mostSeen;
				for(elt in result.coursByCategories){
					if(0 != result.coursByCategories[elt]){
						$scope.model.categoriesLabels.push($filter('translate')(elt));
						$scope.model.categoriesData.push(result.coursByCategories[elt]);
					}
				}
			});
		};
		
	});