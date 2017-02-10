/**
 * Essai de service REST pour la recherche de profils
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('profilService', function($http, config) {
	return {
		allProfiles : function() {
			return $http.get(config.localServer+"/profile/all");
		},
		search : function(query) {
			return $http.get(config.localServer+"/profile?query=" + query);
		},
		allBadges : function() {
			return $http.get(config.localServer+"/badge/all");
		}
	};
});
