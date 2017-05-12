/**
 * WS pour la gestion des villes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('cityService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
	    getById : function(id, user) {
			return $http.get(config.remoteServer+"/city?id="+id+"&user="+user);
		},
		getAll : function(id) {
			return $http.get(config.remoteServer+"/city/all?id="+id);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/city/update", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/city/delete", data, params);
		},
		validate : function(data) {
			return $http.post(config.remoteServer+"/city/validate", data, params);
		},
		cover : function(data) {
			return $http.post(config.remoteServer+"/city/cover", data, params);
		}
	};
});