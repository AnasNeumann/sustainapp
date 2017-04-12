/**
 * WS pour la gestion des topics
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('topicService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(topic, id) {
			return $http.get(config.remoteServer+"/topic?topic="+topic+"&id="+id);
		},
		create : function(data){
			return $http.post(config.remoteServer+"/topic", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/topic/update", data, params);
		},
		picture : function(data) {
			return $http.post(config.remoteServer+"/topic/picture", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/topic/delete", data, params);
		},
		drop : function(data) {
			return $http.post(config.remoteServer+"/topic/drop", data, params);
		}
	};
});