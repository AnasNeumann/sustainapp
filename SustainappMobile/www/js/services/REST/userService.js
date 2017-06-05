/**
 * Essai de service REST pour la gestion de compte utilisateur
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('userService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		signin : function(data) {
			return $http.post(config.remoteServer+"/signin", data, params);
		},
		login : function(data) {
			return $http.post(config.remoteServer+"/login", data, params);
		},
		logout : function(data) {
			return $http.post(config.remoteServer+"/logout", data, params);
		},
		session : function(data) {
			return $http.post(config.remoteServer+"/session", data, params);
		},
		refresh : function(data) {
			return $http.post(config.remoteServer+"/refresh", data, params);
		}
	};
});