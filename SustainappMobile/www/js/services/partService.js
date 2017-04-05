/**
 * WS pour la gestion des partie d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('partService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		create : function(data){
			return $http.post(config.remoteServer+"/part", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/part/update", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/part/delete", data, params);
		}
	};
});