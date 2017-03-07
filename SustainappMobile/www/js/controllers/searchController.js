/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('searchController', function($scope, searchService) {
		
		/**
		 * Initialisation du model
		 */
		var initPage = function(){
			$scope.searchModel = {};
			$scope.searchModel.empty = true;
		};
		initPage();
		
		/**
		 * Fonction de recherche de profile et teams
		 */
		$scope.search = function(){
			searchService.search($scope.searchModel.query).then(function(response){
				$scope.searchModel.empty = true;
				if(response.data.code == 1) {
					if(response.data.profiles.length > 0 || response.data.teams.length > 0){
						$scope.searchModel.empty = false;
					}
					$scope.searchModel.teams = response.data.teams;
					$scope.searchModel.profiles = response.data.profiles;
				} else {
					$scope.searchModel.teams = [];
					$scope.searchModel.profiles = [];
				}
			});
		}
		
	});