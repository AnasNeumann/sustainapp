/**
 * WS pour la gestion des notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('placeService', function($http, config) {
	 var params = {
			withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	 };
	 return {
		getById : function(place, user) {
			return $http.get(config.remoteServer+"/place?place="+place+"&user="+user);
		},
		create : function(data) {
			return $http.post(config.remoteServer+"/place", data, params);
		},
		update : function(data) {
			return $http.post(config.remoteServer+"/place/update", data, params);
		},
		deleteById : function(data) {
			return $http.post(config.remoteServer+"/place/delete", data, params);
		},
		addPicture : function(data) {
			return $http.post(config.remoteServer+"/place/picture/add", data, params);
		},
		delPicture : function(data) {
			return $http.post(config.remoteServer+"/place/picture/add", data, params);
		},
		note : function(data) {
			return $http.post(config.remoteServer+"/place/note", data, params);
		},
		getNear : function(lng, lat) {
			return $http.get(config.remoteServer+"/place/near?lng="+lng+"&lat="+lat);
		}
	};
});