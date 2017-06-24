/**
 * WS pour la gestion des réponses
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('answerService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined,
	        	'Access-Control-Allow-Origin' : config.remoteServer
            },
	        transformRequest: angular.identity
	 };
	 return {
		create : function(data){
			return $http.post(config.remoteServer+"/answer", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/answer/delete", data, params);
		},
		drop : function(data) {
			return $http.post(config.remoteServer+"/answer/drop", data, params);
		}
	};
});