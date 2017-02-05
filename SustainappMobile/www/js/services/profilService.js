/**
 * Essai de service REST pour la recherche de profils
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
// service d'appel d'un controller
.factory('profilService', function($http) {
	return {
		allProfiles : function() {
			return $http.get("http://127.0.0.1:8085/profile/all");
		},
		search : function(query) {
			return $http.get("http://127.0.0.1:8085/profile?query=" + query);
		}
	};
});