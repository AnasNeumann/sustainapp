/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $state, $stomp, $filter, $rootScope, $cordovaLocalNotification, sessionService, userService, config, displayService) {
		
		/**
		 * Initialisation du model
		 */
		var initLoginModel = function(){
			$scope.title = "...";
			$scope.nbrNotification = 0;
			$scope.loginModel = {};
			$scope.loginModel.mail = "";
			$scope.loginModel.read = false;
			$scope.loginModel.modeCity = false;
			$scope.loginModel.password = "";
			$scope.loginModel.firstName = "";
			$scope.loginModel.lastName = "";
			$scope.loginModel.city = "";
			$scope.loginModel.phone = "";
			$scope.loginModel.isConnected = false;
			$scope.loginModel.modeLogin = true;
			$scope.loginModel.allErrors = [];			
			if(null != sessionService.get('mail') && null != sessionService.get('password')){
				$scope.loginModel.mail = sessionService.get('mail');
				$scope.loginModel.password = sessionService.get('password');
			}
			$scope._isNotMobile = displayService.isNotMobile;
		};
		initLoginModel();
		
		/**
		 * Initialisation de la réception de websockets
		 */
		var initWebSocket = function(){			
			$scope.notifications = [];
			$scope.newNotifications = {};
			$stomp.connect(config.remoteServer+'/sustainapp-websocket/', {}).then(function (frame) {
                var subscription = $stomp.subscribe('/queue/notification-'+sessionService.getObject("profile").id, function (payload, headers, res){
                	    $scope.newNotifications = payload;
                        $scope.$apply($scope.newNotifications);
                });
			});
		};
		initWebSocket();
		
		/**
		 * Reception et affichage d'une nouvelle notification
		 */
		$scope.$watch('newNotifications', function() {			
			if(null != $scope.newNotifications.message){
				$scope.notifications.push($scope.newNotifications);
				if(!displayService.isNotMobile){
			        $cordovaLocalNotification.schedule({
			            id: $scope.newNotifications.id,
			            message: $scope.newNotifications.creator+" "+$filter('translate')($scope.newNotifications.message)+" "+$scope.newNotifications.target,
			            title: "Sustainapp !",
			            autoCancel: true,
			            sound: null,
			            icon : "notification/"+$scope.newNotifications.message.replace('.','')+".png"
			        }).then(function () {
			        });
				}
				$scope.nbrNotification = $scope.notifications.length;
			}
	    });
		
		/**
		 * Fonction d'annulation des nouvelles notifications
		 */
		$scope.getNotifications = function(){
			$scope.notifications = [];
			$scope.nbrNotification = 0;
			$state.go('tab.notifications');		
		};

		/**
		 * fonction d'inscription
		 */
		$scope.signin = function(){
			var data = new FormData();
			data.append("mail", $scope.loginModel.mail);
			data.append("lastName", $scope.loginModel.lastName);
			data.append("firstName", $scope.loginModel.firstName);
			data.append("password", $scope.loginModel.password);
			data.append("phone", $scope.loginModel.phone);
			data.append("city", $scope.loginModel.city);
			data.append("type", ($scope.loginModel.modeCity==true)? 1 : 0);
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
	    		$scope.loginModel.type = result.userType;
	    		sessionService.setObject('profile' ,result.profile);
	    		sessionService.set('id' ,result.id);
	    		sessionService.set('token' ,result.token);
	    		sessionService.set('isConnected' ,"true");
		    	$scope.loginModel.isConnected = true;
		    	$scope.loginModel.isAdmin = result.isAdmin;
		    	if(1 == result.userType){
		    		sessionService.setObject('city' ,result.city);
		    		$scope.loginModel.cityId = result.city.id;
		    	}
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