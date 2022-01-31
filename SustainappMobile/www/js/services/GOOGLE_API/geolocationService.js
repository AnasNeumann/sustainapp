/**
 * WS d'appel des API Google de géolocalisation (Map, Direction ...)
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.services').factory('geolocationService', function($http, config) {
	var BASE_API = "https://maps.googleapis.com/maps/api/";
	var API_KEY = "*****";
	return {
		getLocationFromAddress : function(Address) {
			return $http.get(BASE_API+"geocode/json?address="+Address+"&key="+API_KEY);
		}
	}
});
