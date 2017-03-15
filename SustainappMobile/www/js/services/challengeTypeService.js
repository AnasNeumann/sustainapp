/**
 * WS pour la gestion des type de challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/03/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('challengeTypeService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getAll : function() {
			return $http.get(config.remoteServer+"/challengetype/all");
		},
		create : function(data){
			return $http.post(config.remoteServer+"/challengetype", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/challengetype/delete", data, params);
		}
	};
});