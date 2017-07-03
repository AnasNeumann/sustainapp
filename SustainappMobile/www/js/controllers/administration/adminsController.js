/**
 * Controller de l'ajout d'un nouvel administrateur
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/07/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers').controller('adminsController', function($scope, $filter, sessionService, administrationService) {
	
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
		$scope.model = {
			"email" : "",
			"error" : ""
		};
	};

	/**
	 * Créer un nouvel administrateur
	 */
	$scope.createAdmin = function(){
		var data = new FormData();
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		data.append("mail", $scope.model.email);
		administrationService.admin(data).success(function(response){
			if(1 == response.code){
				$scope.model.error = "";
				alert(response.denomination+" "+$filter('translate')("administration.nowAdmin"));
			}else{
				if("" == $scope.model.email){
					$scope.model.error = "form.mail.mandatory";
				}else{
					$scope.model.error = "form.mail.notExist";
				}
			}
		}).error(function(error){
	    	sessionService.refresh($scope.createAdmin);
	    });
	};

});