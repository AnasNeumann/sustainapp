/**
 * WS pour la gestion de la session locales
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
 .factory('sessionService', function($window) {
	 return {
		set: function(key, value) {
			 $window.localStorage[key] = value;
		 	},
	    get: function(key, defaultValue) {
		      return $window.localStorage[key] || defaultValue;
		    },
	    setObject: function(key, value) {
		      $window.localStorage[key] = JSON.stringify(value);
		    },
	    getObject: function(key) {
		      return JSON.parse($window.localStorage[key] || '{}');
		    }		    
	};
});
