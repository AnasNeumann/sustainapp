/**
 * WS pour la gestion des signalement
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 16/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('reportService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getAll : function() {
			return $http.get(config.remoteServer+"/report");
		},
		create : function(data) {
			return $http.post(config.remoteServer+"/report", data, params);
		},
		update : function(data) {
			return $http.put(config.remoteServer+"/report", data, params);
		},
		deleteById : function(data) {
			return $http.delete(config.remoteServer+"/report", data, params);
		}
	};
});
