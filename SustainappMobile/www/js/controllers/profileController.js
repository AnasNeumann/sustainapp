/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('profileController', function($scope, $stateParams, $filter, $cordovaFile, $cordovaFileTransfer, $cordovaDevice, sessionService, profileService, fileService) {
		
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
				loadProfile();
	        });
		
		/**
		 * Fonction initiale d'affichage de la page
		 */
		var loadProfile = function(){
			$scope.profileModel = {};
			$scope.profileModel.profile = {};
			$scope.profileModel.coverEdit = false;
			$scope.profileModel.avatarEdit = false;
			$scope.profileModel.modeRead = true; 
			$scope.profileModel.loaded = false;
			$scope.profileModel.owner = false;
			$scope.profileModel.displayAvatar = "img/common/defaultAvatarMin.png";
			$scope.profileModel.displayCover = null;
			profileService.getById($stateParams.id).then(function(response){
				if(response.data.code == 1) {
					 response.data.profiles[0].bornDate = new Date(response.data.profiles[0].bornDate);
	    			 $scope.profileModel.profile = response.data.profiles[0];
	    			 $scope.profileModel.profileTemp = response.data.profiles[0];
	    			 $scope.profileModel.loaded = true;
		    		 $scope.profileModel.allErrors = [];
		    		 if(null != response.data.profiles[0].base64Avatar && "" != response.data.profiles[0].base64Avatar){
		    			 $scope.profileModel.displayAvatar = "data:image/jpeg;base64,"+ response.data.profiles[0].base64Avatar;
		    		 } 
		    		 if(null != response.data.profiles[0].base64Cover && "" != response.data.profiles[0].base64Cover){
		    			 $scope.profileModel.displayCover = "data:image/jpeg;base64,"+ response.data.profiles[0].base64Cover;
		    		 }
		    		 if(response.data.profiles[0].id == sessionService.getObject('profile').id){
		    			 $scope.profileModel.owner = true;
		    		 }
	    		 }
	     	  });
	    };
	    
	    /**
	     * update d'un profile
	     */
	    $scope.updateProfile = function(){
	    	var data = new FormData();
			data.append("firstName", $scope.profileModel.profileTemp.firstName);
			data.append("lastName", $scope.profileModel.profileTemp.lastName);
			data.append("bornDate", $scope.profileModel.profileTemp.bornDate);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			profileService.update(data).success(function(result) {
				if(result.code == 1){
		    		$scope.profileModel.allErrors = [];
		    		$scope.profileModel.profile.firstName = $scope.profileModel.profileTemp.firstName;
			    	$scope.profileModel.profile.lastName = $scope.profileModel.profileTemp.lastName;
			    	$scope.profileModel.profile.bornDate = $scope.profileModel.profileTemp.bornDate;
			    	$scope.profileModel.modeRead = true;
			    	$scope.profileModel.coverEdit = false;
					$scope.profileModel.avatarEdit = false;
		    	} else {
		    		$scope.profileModel.allErrors = result.errors;
		    	}
		    });
	    };
	    
	    /**
	     * update cover file
	     */
	    $scope.cover = function(newFile){
	    	fileService.getFile(newFile, 100, 300, 120).then(function(imageData) {
	    		var data = new FormData();
				data.append("file", imageData);
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				profileService.cover(data).success(function(result) {
					if(result.code == 1){
						$scope.profileModel.displayCover = "data:image/jpeg;base64,"+imageData;
						$scope.profileModel.coverEdit = false;
						$scope.profileModel.avatarEdit = false;
					}
				});
			 }, function(err) {
			 });
	    };
	    
	    /**
	     * update avatar file
	     */
	    $scope.avatar = function(newFile){
	    	var base64 = fileService.getFile(newFile, 100, 150, 150).then(function(imageData) {
	    		var data = new FormData();
				data.append("file", imageData);
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				profileService.avatar(data).success(function(result) {
					if(result.code == 1){
						$scope.profileModel.displayAvatar = "data:image/jpeg;base64,"+imageData;
						$scope.profileModel.coverEdit = false;
						$scope.profileModel.avatarEdit = false;
					}
				});
			 }, function(err) {
			 });	    	
	    }
				
	});