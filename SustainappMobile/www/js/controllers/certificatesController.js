/**
 * Essai de controller de databinding
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('certificatesController', function($scope, profilService) {
  $scope.settings = {
    enableFriends: true
  };
  
	var badgeService = function(){
	  $scope.loadingProfiles = true;
	  profilService.allBadges().then(function(response){
		  $scope.badges = response.data;
		  $scope.loadingProfiles = false;
	  });
	};
		    
	badgeService();
});