/**
 * WS pour la gestion des profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('profileService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined,
	        	'Access-Control-Allow-Origin' : config.remoteServer
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(id) {
			return $http.get(config.remoteServer+"/profile?id="+id, params);
		},
		update : function(data){
			return $http.post(config.remoteServer+"/profile", data, params);
		},
		avatar : function(data) {
			return $http.post(config.remoteServer+"/profile/avatar", data, params);
		},
		cover : function(data) {
			return $http.post(config.remoteServer+"/profile/cover", data, params);
		},
		visibility : function(data) {
			return $http.post(config.remoteServer+"/profile/visibility", data, params);
		},
		deleteById : function(data){
			return $http.post(config.remoteServer+"/profile/delete", data, params);
		}
	};
});
