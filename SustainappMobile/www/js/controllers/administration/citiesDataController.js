/**
 * Controller de la visualisation des données
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('citiesDataController', function($scope, $filter, sessionService, administrationService) {
		
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
			$scope.model.noteData = [];
			$scope.model.noteLabels = [];
			$scope.model.moreViews = [];
			$scope.model.seriesHours = [$filter('translate')("administration.byHours")];
			$scope.model.seriesDays = [$filter('translate')("administration.byDays")];
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			administrationService.cities(data).success(function(result) {
				console.log(result);
				$scope.model.loaded = true;
				$scope.model.totalCities = result.totalCities;
				$scope.model.totalPlaces = result.totalPlaces;
				$scope.model.averagePictures = result.averagePictures;
				$scope.model.moreViews = result.moreViews;
				var tempHours = [];
				for(elt in result.visitByHours){
					$scope.model.hoursLabels.push(elt);
					tempHours.push(result.visitByHours[elt]);
				}
				$scope.model.hoursData.push(tempHours);
				var tempDays = [];
				for(elt in result.visitByDays){
					$scope.model.daysLabels.push($filter('translate')(elt));
					tempDays.push(result.visitByDays[elt]);
				}
				$scope.model.daysData.push(tempDays);
				for(elt in result.placeByNotes){			
					$scope.model.noteLabels.push(elt);
					$scope.model.noteData.push(result.placeByNotes[elt]);					
				}
			});
		};
	});