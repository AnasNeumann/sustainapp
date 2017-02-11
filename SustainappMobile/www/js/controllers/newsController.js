/**
 * Essai de controller d'appel de webservice rest
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('newsController', function($scope, profilService) {
	
	$scope.model = {};
	
    $scope.searchFx = function(){
    	 $scope.loadingSearch = true;
    	 profilService.search($scope.model.maVar).then(function(response){
    		  $scope.result = response;
     		  $scope.loadingSearch = false;
     	  }); 	
    };
});