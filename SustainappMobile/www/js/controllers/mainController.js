/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $state, sessionService, userService) {
		
		/**
		 * Initialisation du model
		 */	
		var initLoginModel = function(){
			$scope.title = "...";
			$scope.loginModel = {};
			$scope.loginModel.mail = "";
			$scope.loginModel.password = "";
			$scope.loginModel.firstName = "";
			$scope.loginModel.lastName = "";
			$scope.loginModel.isConnected = false;
			$scope.loginModel.modeLogin = true;
			$scope.loginModel.allErrors = [];
			if(null != sessionService.get('mail') && null != sessionService.get('password')){
				$scope.loginModel.mail = sessionService.get('mail');
				$scope.loginModel.password = sessionService.get('password');
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
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			userService.logout(data).success(function(result) {		    	
				$scope.loginModel.isConnected = false;
				sessionService.set('id' ,null);
				sessionService.set('token' ,null);
				sessionService.set('isConnected' ,null);
				$scope.loginModel.isAdmin = false;
		    });
		}

		/**
		 * fonction de verification si on est connecté executée au démarage de l'application
		 */
		var initialConnection = function(){
			if(null != sessionService.get('mail') && null != sessionService.get('password') && "true" == sessionService.get('isConnected')){
				var data = new FormData();
				data.append("mail", sessionService.get('mail'));
				data.append("password", sessionService.get('password'));
				userService.login(data).success(function(result) {		    	
			    	openSession(result, sessionService.get('mail'), sessionService.get('password'));
			    });
			}
		}
		initialConnection();
		
		/**
		 * Fonction commune d'ouverture local d'une session
		 * @param result
		 */
		var openSession = function (result, mail, password){			
			sessionService.set('mail' ,mail);
    		sessionService.set('password' ,password);
			if(result.code == 1){
	    		$scope.loginModel.allErrors = [];    		
	    		$scope.loginModel.profileId = result.profile.id;  
	    		sessionService.setObject('profile' ,result.profile);
	    		sessionService.set('id' ,result.id);
	    		sessionService.set('token' ,result.token);
	    		sessionService.set('isConnected' ,"true");
		    	$scope.loginModel.isConnected = true;
		    	$scope.loginModel.isAdmin = result.isAdmin;
		    	$state.go('tab.news');
	    	} else {
	    		$scope.loginModel.allErrors = result.errors;
	    	}			
		}
		
		/**
		 * Affichage du journal d'actualités
		 */
		$scope.displayNews = function(){
			$state.go('tab.news');
		}

	});