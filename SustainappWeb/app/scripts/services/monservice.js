'use strict';

/**
 * @ngdoc service
 * @name sustainappWebApp.monService
 * @description
 * # monService
 * Factory in the sustainappWebApp.
 */
angular.module('sustainappWebApp')
  .factory('monService', function ($http) {
    return {
      allProfiles: function () {
        return $http.get("http://127.0.0.1:8085/profile/all");
      }
    };
  })
  .factory('recherche', function ($http) {
    return {
    	recherche: function (query) {
          return $http.get("http://127.0.0.1:8085/profile?query="+query);
        }
      };
  })
