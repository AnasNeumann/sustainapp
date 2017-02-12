/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $http, config, session) {
		
		/**
		 * Initialisation du model
		 */
		$scope.loginModel = {};
		$scope.loginModel.mail = "";
		$scope.loginModel.password = "";
		$scope.loginModel.firstName = "";
		$scope.loginModel.lastName = "";
		$scope.loginModel.isConnected = false;
		$scope.loginModel.modeLogin = true;
		$scope.loginModel.allErrors = [];
			
		/**
		 * fonction d'inscription
		 */
		$scope.signin = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("lastName", $scope.loginModel.lastName);
			data.append("firstName", $scope.loginModel.firstName);
			data.append("password", $scope.loginModel.password);
			
			$http.post(config.localServer+"/signin", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	if(result.code == 1){
		    		$scope.loginModel.allErrors = [];
		    		$scope.loginModel.isConnected = true;
			    	session.profile = result.profile;
		    	} else {
		    		$scope.loginModel.allErrors = result.errors;
		    	}
		    });
		}

		/**
		 * fonction de connexion
		 */
		$scope.login = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("password", $scope.loginModel.password);
			
			$http.post(config.localServer+"/login", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	if(result.code == 1){
		    		$scope.loginModel.allErrors = [];
		    		$scope.loginModel.isConnected = true;
			    	session.profile = result.profile;
		    	} else {
		    		$scope.loginModel.allErrors = result.errors;
		    	}
		    });
		}

		/**
		 * fonction de deconnexion
		 */
		$scope.logout = function(){
			$http.get(config.localServer+"/logout").then(function(response){
				if(response.data.code == 1){
					session = {};
					$scope.loginModel.isConnected = false;
				}
		  	});
		}

		/**
		 * fonction de verification si on est connecté executée au démarage de l'application
		 */
		$scope.getSession = function(){
			return null;
		}
		$scope.getSession();

	});