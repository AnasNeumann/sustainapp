/**
 * WS pour la gestion des notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/05/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('notificationService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getAll : function(data) {
			return $http.post(config.remoteServer+"/notification/all", data, params);
		},
		read : function(data) {
			return $http.post(config.remoteServer+"/notification/read", data, params);
		}
	};
});