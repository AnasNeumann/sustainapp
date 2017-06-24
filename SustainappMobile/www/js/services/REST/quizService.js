/**
 * WS pour la validation d'un quiz
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('quizService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined,
	        	'Access-Control-Allow-Origin' : config.remoteServer
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(topic) {
				return $http.get(config.remoteServer+"/quiz?topic="+topic, params);
		},
		validateQuiz : function(data) {
			return $http.post(config.remoteServer+"/quiz", data, params);
		}
	};
});