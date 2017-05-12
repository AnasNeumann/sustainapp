/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('searchController', function($scope, searchService, sessionService) {
		
		/**
		 * Initialisation du model
		 */
		var initPage = function(){
			$scope.searchModel = {};
			$scope.searchModel.empty = true;
			$scope.searchModel.flag = false;
		};
		initPage();
		
		/**
		 * Fonction de recherche de profile et teams
		 */
		$scope.search = function(){
			searchService.search($scope.searchModel.query).then(function(response){
				$scope.searchModel.empty = true;
				if($scope.searchModel.query.length >= 4){
					$scope.searchModel.flag = true;
				}
				if(response.data.code == 1) {
					if(response.data.cities.length > 0 || response.data.profiles.length > 0 || response.data.teams.length > 0 || response.data.courses.length > 0){
						$scope.searchModel.empty = false;
					}
					$scope.searchModel.teams = response.data.teams;
					$scope.searchModel.profiles = response.data.profiles;
					$scope.searchModel.courses = response.data.courses;
					$scope.searchModel.cities = response.data.cities;
				} else {
					$scope.searchModel.teams = [];
					$scope.searchModel.profiles = [];
					$scope.searchModel.courses = [];
					$scope.searchModel.cities = [];
				}
			});
		};
		
		/**
		 * Fonction de sauvegarde de la recherche en base
		 */
		$scope.save = function(){
			if(true == $scope.searchModel.flag){
				$scope.searchModel.flag = false;
				var data = new FormData();
				data.append("query", $scope.searchModel.query);
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				searchService.save(data);
			}
		}
		
	});