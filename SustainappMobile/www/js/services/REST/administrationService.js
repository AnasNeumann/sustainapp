/**
 * WS pour la gestion de l'accès aux informations
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
angular.module('sustainapp.services').factory('administrationService', function($http, config) {
	 var params = {
		withCredentials: true,
        headers: {
        	'Content-Type': undefined
        },
        transformRequest: angular.identity
	 };
	 return {
		courses : function(data) {
				return $http.post(config.remoteServer+"/administration/courses", data ,params);
		},
		challenges : function(data) {
			return $http.post(config.remoteServer+"/administration/challenges", data ,params);
		},
		profiles : function(data) {
			return $http.post(config.remoteServer+"/administration/profiles", data ,params);
		},
		research : function(data) {
			return $http.post(config.remoteServer+"/administration/research", data ,params);
		}
	};
});