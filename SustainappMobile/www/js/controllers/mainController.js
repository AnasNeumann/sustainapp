/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $window, $state, userService, session) {
		
		/**
		 * Initialisation du model
		 */	
		var initLoginModel = function(){
			$scope.loginModel = {};
			$scope.loginModel.mail = "";
			$scope.loginModel.password = "";
			$scope.loginModel.firstName = "";
			$scope.loginModel.lastName = "";
			$scope.loginModel.isConnected = false;
			$scope.loginModel.modeLogin = true;
			$scope.loginModel.allErrors = [];
			if(null != $window.localStorage['mail'] && null != $window.localStorage['password']){
				$scope.loginModel.mail = $window.localStorage['mail'];
				$scope.loginModel.password = $window.localStorage['password'];
			}
		};
		initLoginModel();
			
		/**
		 * fonction d'inscription
		 */
		$scope.signin = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("lastName", $scope.loginModel.lastName);
			data.append("firstName", $scope.loginModel.firstName);
			data.append("password", $scope.loginModel.password);
			userService.signin(data).success(function(result) {		    	
		    	openSession(result, $scope.loginModel.mail, $scope.loginModel.password);
		    });
		}

		/**
		 * fonction de connexion
		 */
		$scope.login = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("password", $scope.loginModel.password);
			userService.login(data).success(function(result) {		    	
		    	openSession(result, $scope.loginModel.mail, $scope.loginModel.password);
		    });
		}

		/**
		 * fonction de deconnexion
		 */
		$scope.logout = function(){
			var data = new FormData();
			data.append("sessionId", session.id);
			data.append("sessionToken", session.token);
			userService.logout(data).success(function(result) {		    	
				$scope.loginModel.isConnected = false;
	    		session = {};
	    		$window.localStorage['isConnected'] = "false";
		    });
		}

		/**
		 * fonction de verification si on est connecté executée au démarage de l'application
		 */
		var initialConnection = function(){
			if(null != $window.localStorage['mail'] && null != $window.localStorage['password'] && "true" == $window.localStorage['isConnected']){
				var data = new FormData();
				data.append("mail", $window.localStorage['mail']);
				data.append("password", $window.localStorage['password']);
				userService.login(data).success(function(result) {		    	
			    	openSession(result, $window.localStorage['mail'], $window.localStorage['password']);
			    });
			}
		}
		initialConnection();
		
		
		/**
		 * Fonction commune d'ouverture local d'une session
		 * @param result
		 */
		var openSession = function (result, mail, password){
			$window.localStorage['mail'] = mail;
	    	$window.localStorage['password'] = password;
			if(result.code == 1){
	    		$scope.loginModel.allErrors = [];
	    		$scope.loginModel.isConnected = true;
		    	session.profile = result.profile;
		    	session.id = result.id;
		    	session.token = result.token;
		    	$window.localStorage['isConnected'] = "true";
	    	} else {
	    		$scope.loginModel.allErrors = result.errors;
	    	}
		}
		
		/**
		 * Affichage du journal d'actualités
		 */
		$scope.displayNews = function(){
			$state.go('tab.news')
		}

	});