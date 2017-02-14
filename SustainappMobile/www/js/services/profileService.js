/**
 * Essai de service REST pour la recherche de profils
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('profileService', function($http, config) {
	return {
		getById : function(id) {
			return $http.get(config.remoteServer+"/profile?id="+id);
		}
	};
});
