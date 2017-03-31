/**
 * WS pour la gestion des challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('coursService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(cours, id) {
			return $http.get(config.remoteServer+"/course?cours="+cours+"&id="+id);
		},
		getAll : function(startIndex) {
			return $http.get(config.remoteServer+"/course/all?startIndex="+startIndex);
		},
		create : function(data){
			return $http.post(config.remoteServer+"/course", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/course/update", data, params);
		},
		picture : function(data) {
			return $http.post(config.remoteServer+"/course/picture", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/course/delete", data, params);
		},
		toogleOpen : function(data) {
			return $http.post(config.remoteServer+"/course/open", data, params);
		},
	};
});