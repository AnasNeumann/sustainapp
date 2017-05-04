/**
 * Essai de controller de communication avec l'appareil photo
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('notificationsController', function($scope, $cordovaLocalNotification, notificationService, displayService, sessionService) {

	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
		loadNotifications();
    });

	/**
	 * Chargement des 20 dernières notifications
	 */
	var loadNotifications = function(){
		$scope.notificationsModel = {};
		$scope.notificationsModel.loaded = false;
		$scope.notificationsModel.notifications = [];
		var data = new FormData();
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		notificationService.getAll(data).success(function(result) {
			if(result.code == 1){
				$scope.notificationsModel.loaded = true;
				$scope.notificationsModel.notifications = result.notifications;
	    	}
	    });
	};
	
	/**
	 * Visiter un lien d'une notification (et la passer à l'état 2)
	 */
	$scope.read = function(notification){
		var data = new FormData();
		data.append("notification", notification.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		notificationService.read(data);
	};
});
