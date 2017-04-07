/**
 * WS pour la gestion des questions
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('questionService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getAll : function(topic, id) {
				return $http.get(config.remoteServer+"/question/all?topic="+topic+"&id="+id);
		},
		getById : function(question, id) {
			return $http.get(config.remoteServer+"/question?question="+question+"&id="+id);
		},
		create : function(data){
			return $http.post(config.remoteServer+"/question", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/question/update", data, params);
		},
		picture : function(data) {
			return $http.post(config.remoteServer+"/question/picture", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/question/delete", data, params);
		},
		drop : function(data) {
			return $http.post(config.remoteServer+"/question/drop", data, params);
		}
	};
});