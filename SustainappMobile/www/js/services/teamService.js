/**
 * WS pour la gestion des equipes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('teamService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(team, id) {
			return $http.get(config.remoteServer+"/team?team="+team+"&id="+id);
		},
		getAll : function(startIndex) {
			return $http.get(config.remoteServer+"/team/all?startIndex="+startIndex);
		},
		create : function(data){
			return $http.post(config.remoteServer+"/team", data, params);
		},
		update : function(data) {
			return $http.put(config.remoteServer+"/team", data, params);
		},
		avatar : function(data) {
			return $http.post(config.remoteServer+"/team/avatar", data, params);
		},
		handleRole : function(data) {
			return $http.post(config.remoteServer+"/team/role", data, params);
		},
		deleteById : function(data) {
			return $http.delete(config.remoteServer+"/team", data, params);
		}
	};
});