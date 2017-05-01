/**
 * Controller pour l'affichage du journal d'actualités
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('newsController', function($scope, newspaperService) {
	
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
		loadNewspaper();
    });
	
	var loadNewspaper = function(){
		$scope.newsModel = {};
		$scope.newsModel.loaded = false;
		newspaperService.getNews().then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.newsModel.loaded = true;
				$scope.newsModel.courses = result.cours;
				$scope.newsModel.participations = result.participations;
			}
		});
	};
	
});