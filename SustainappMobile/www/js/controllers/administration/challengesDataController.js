/**
 * Controller de la visualisation des données
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('challengesDataController', function($scope, $filter, sessionService, administrationService) {
		
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
			$scope.model.hoursLabels = [];
			$scope.model.hoursData = [];
			$scope.model.daysLabels = [];
			$scope.model.daysData = [];
			$scope.model.categoriesData = [];
			$scope.model.categoriesLabels = [];
			$scope.model.seriesHours = [$filter('translate')("administration.byHours")];
			$scope.model.seriesDays = [$filter('translate')("administration.byDays")];
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			administrationService.challenges(data).success(function(result) {
				$scope.model.loaded = true;
				$scope.model.total = result.total;
				$scope.model.average = result.average;
				var tempHours = [];
				for(elt in result.useByHours){
					$scope.model.hoursLabels.push(elt);
					tempHours.push(result.useByHours[elt]);
				}
				$scope.model.hoursData.push(tempHours);
				var tempDays = [];
				for(elt in result.useByDays){
					$scope.model.daysLabels.push($filter('translate')(elt));
					tempDays.push(result.useByDays[elt]);
				}
				$scope.model.daysData.push(tempDays);
				for(elt in result.challengesByCategories){
					if(0 != result.challengesByCategories[elt]){
						$scope.model.categoriesLabels.push($filter('translate')(elt));
						$scope.model.categoriesData.push(result.challengesByCategories[elt]);
					}
				}
			});
		};
	});