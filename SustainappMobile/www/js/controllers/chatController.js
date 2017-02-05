/**
 * Essai de controller d'affichage d'une liste de profil
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('ChatsCtrl', function($scope, Chats, profilService) {
	
	var profileService = function(){
  	  $scope.loadingProfiles = true;
  	  profilService.allProfiles().then(function(response){
  		  $scope.profiles = response.data;
  		  $scope.loadingProfiles = false;
  	  });
    };

    
  profileService();
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
  
});