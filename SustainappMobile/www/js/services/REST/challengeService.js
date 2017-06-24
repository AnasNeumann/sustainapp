/**
 * WS pour la gestion des challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('challengeService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined,
	        	'Access-Control-Allow-Origin' : config.remoteServer
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(challenge, id) {
			return $http.get(config.remoteServer+"/challenge?challenge="+challenge+"&id="+id);
		},
		getAll : function(startIndex) {
			return $http.get(config.remoteServer+"/challenge/all?startIndex="+startIndex);
		},
		types : function() {
			return $http.get(config.remoteServer+"/challenge/types");
		},
		create : function(data){
			return $http.post(config.remoteServer+"/challenge", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/challenge/update", data, params);
		},
		icon : function(data) {
			return $http.post(config.remoteServer+"/challenge/icon", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/challenge/delete", data, params);
		}
	};
});