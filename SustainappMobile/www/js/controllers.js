/**
 * Fichier contenant tous les controllers sur les vues dans notre projet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope, profilService) {
	
	$scope.model = {};
	
    $scope.searchFx = function(){
    	 $scope.loadingSearch = true;
    	 profilService.search($scope.model.maVar).then(function(response){
    		  $scope.result = response;
     		  $scope.loadingSearch = false;
     	  }); 	
    };
})

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
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
