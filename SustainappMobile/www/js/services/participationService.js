/**
 * WS pour la gestion des participations
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/03/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('participationService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		create : function(data) {
			return $http.post(config.remoteServer+"/participation/create", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/participation/delete", data, params);
		},
		vote : function(data) {
			return $http.post(config.remoteServer+"/participation/vote", data, params);
		},
		getVotes : function(participation) {
			return $http.get(config.remoteServer+"/participation/votes?participation="+participation);
		}
	};
});