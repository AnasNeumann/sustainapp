/**
 * Controller principal de l'application
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('mainController', function($scope, $state, $stomp, sessionService, userService, config) {
		
		/**
		 * Initialisation du model
		 */
		var initLoginModel = function(){
			$scope.title = "...";
			$scope.nbrNotification = 0;
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
		 * Initialisation de la réception de websockets
		 */
		var initWebSocket = function(){			
			$scope.notifications = [];
			$scope.newNotifications = {};
			$stomp.connect(config.remoteServer+'/sustainapp-websocket', {}).then(function (frame) {
                var subscription = $stomp.subscribe('/websocket/notification', function (payload, headers, res){
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
				$scope.nbrNotification = $scope.notifications.length;
			}
			console.log($scope.notifications);
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
		
		/**
		 * Function to detect if is not mobile view
		 */
		var _isNotMobile = (function() {
	        var check = false;
	        (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
	        return !check;
	    })();

	});