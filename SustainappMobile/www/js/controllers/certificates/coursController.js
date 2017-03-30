/**
 * Controller pour l'affichage principal d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('coursController', function($scope, $ionicPopover, $state, sessionService, coursService, fileService, listService, displayService) {

	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadCours();
        });
		
	/**
	 * Chargement des informations du cours
	 */
	var loadCours = function(){
		$scope.coursModel = {};
		$scope.coursModel.loaded = false;
		$scope.title = "...";

		$scope.coursModel.emptyFile = true;
		$scope.coursModel.edit = false;
		$scope.coursModel.pictureEdit = false;
		$scope.coursModel.file  = null;

		$scope._isNotMobile = displayService.isNotMobile;
		$scope.coursModel.allErrors = [];	
		coursService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				console.log(resut);
			}
		});
	}
	
});