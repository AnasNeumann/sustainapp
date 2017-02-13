/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $http, $window, config, session) {
		
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
			
			$http.post(config.remoteServer+"/signin", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	openSession(result);
		    });
		}

		/**
		 * fonction de connexion
		 */
		$scope.login = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("password", $scope.loginModel.password);
			
			$http.post(config.remoteServer+"/login", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	openSession(result);
		    });
		}

		/**
		 * fonction de deconnexion
		 */
		$scope.logout = function(){
			var data = new FormData();
			data.append("sessionId", session.id);
			data.append("sessionToken", session.token);
			
			$http.post(config.remoteServer+"/logout", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	if(result.code == 1){
		    		session = {};
		    		$window.localStorage['id'] = null;
		    		$window.localStorage['token'] = null;
					$scope.loginModel.isConnected = false;
		    	}
		    });
		}

		/**
		 * fonction de verification si on est connecté executée au démarage de l'application
		 */
		$scope.initialConnection = function(){
			if(null == $window.localStorage['id'] || null == $window.localStorage['token']){
				return;
			}
			var data = new FormData();
			data.append("sessionId", $window.localStorage['id']);
			data.append("sessionToken", $window.localStorage['token']);
			$http.post(config.remoteServer+"/session", data, {
		        withCredentials: true,
		        headers: {
		        	'Content-Type': undefined
	            },
		        transformRequest: angular.identity
		    }).success(function(result) {		    	
		    	openSession(result);
		    });
		}
		$scope.initialConnection();
		
		
		/**
		 * Fonction commune d'ouverture local d'une session
		 */
		var openSession = function (result){
			if(result.code == 1){
	    		$scope.loginModel.allErrors = [];
	    		$scope.loginModel.isConnected = true;
		    	session.profile = result.profile;
		    	session.id = result.id;
		    	session.token = result.token;
		    	$window.localStorage['id'] = session.id;
		    	$window.localStorage['token'] = session.token;
	    	} else {
	    		$scope.loginModel.allErrors = result.errors;
	    	}
		}

	});