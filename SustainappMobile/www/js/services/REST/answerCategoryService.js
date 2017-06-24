/**
 * WS pour la gestion des catégories de réponses
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('answerCategoryService', function($http, config) {
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
			return $http.post(config.remoteServer+"/category", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/category/delete", data, params);
		},
		drop : function(data) {
			return $http.post(config.remoteServer+"/category/drop", data, params);
		}
	};
});