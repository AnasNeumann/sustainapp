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
		    	$scope.loginModel.isConnected = true;
		    	session.profile = result.profile;
		    });
		}		
		
		/**
		 * fonction de connexion
		 */
		$scope.login = function(){
			return null;
		}

		/**
		 * fonction de deconnexion
		 */
		$scope.logout = function(){
			return null;
		}

		/**
		 * fonction de verification si on est connecté
		 */
		$scope.getSession = function(){
			
		}

	});