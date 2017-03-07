/**
 * WS pour la gestion des recherches
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('searchService', function($http, config) {
	 return {
		search : function(query) {
			return $http.get(config.remoteServer+"/search?query="+query);
		}
	};
});
