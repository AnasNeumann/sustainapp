/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('profileController', 
			function($scope, $stateParams, $filter, $ionicModal, $cordovaFile, $cordovaFileTransfer, $cordovaDevice, sessionService, profileService, fileService, displayService) {
		
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
			$scope.title = "";
			$scope.profileModel = {};
			$scope.profileModel.profile = {};
			$scope.profileModel.courses = [];
			$scope.profileModel.badges = [];
			$scope.profileModel.coverEdit = false;
			$scope.profileModel.avatarEdit = false;
			$scope.profileModel.modeRead = true; 
			$scope.profileModel.loaded = false;
			$scope.profileModel.owner = false;
			$scope.profileModel.displayAvatar = "img/common/defaultAvatarMin.png";
			$scope.profileModel.displayCover = null;
			$scope.currentBadge = {};
			profileService.getById($stateParams.id).then(function(response){
				if(response.data.code == 1) {
					 response.data.profile.bornDate = new Date(response.data.profile.bornDate);
					 $scope.title = response.data.profile.firstName+" "+response.data.profile.lastName;
					 $scope.profileModel.profile = response.data.profile;
	    			 $scope.profileModel.profileTemp = response.data.profile;
	    			 $scope.profileModel.loaded = true;
	    			 $scope.profileModel.badges = response.data.badges;
	    			 $scope.profileModel.courses = response.data.courses;
		    		 $scope.profileModel.allErrors = [];
		    		 $scope.profileModel.visibility = (response.data.profile.visibility == 1);
		    		 if(null != response.data.profile.avatar && "" != response.data.profile.avatar){
		    			 $scope.profileModel.displayAvatar = "data:image/jpeg;base64,"+ response.data.profile.avatar;
		    		 }
		    		 if(null != response.data.profile.cover && "" != response.data.profile.cover){
		    			 $scope.profileModel.displayCover = "data:image/jpeg;base64,"+ response.data.profile.cover;
		    		 }
		    		 if(response.data.profile.id == sessionService.getObject('profile').id){
		    			 $scope.profileModel.owner = true;
		    		 }
	    		 }
	     	  });
	        $scope._isNotMobile = displayService.isNotMobile;
	        $scope.profileModel.openCourses = true;
	        $scope.profileModel.openBadges = true;
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
		    })
		    .error(function(error){
		    	sessionService.refresh($scope.updateProfile);
		    });
	    };
	    
	    /**
	     * update cover file [mobile mode]
	     */
	    $scope.cover = function(newFile){
	    	fileService.getFile(newFile, 100, 600, 240, false).then(function(imageData) {
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
				}).error(function(error){
			    	sessionService.refresh(null);
			    });
			 }, function(err) {
			 });
	    };
	    
	    /**
	     * update avatar file [mobile mode]
	     */
	    $scope.avatar = function(newFile){
	    	var base64 = fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
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
				}).error(function(error){
			    	sessionService.refresh(null);
			    });
			 }, function(err) {
			 });
	    }
	    
	    /**
	     * update avatar file [desktop mode]
	     */
	    $scope.desktopAvatar = function(input){
	    	var reader = new FileReader();
            reader.onload = function (e) {
            	var data = new FormData();
				data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				profileService.avatar(data).success(function(result) {
					if(result.code == 1){
						$scope.profileModel.displayAvatar = e.target.result;
					}
				}).error(function(error){
			    	sessionService.refresh(null);
			    });
            }
            reader.readAsDataURL(input.files[0]);  
	    }
	    
	    /**
	     * update cover file [desktop mode]
	     */
	    $scope.desktopCover = function(input){
	    	var reader = new FileReader();
            reader.onload = function (e) {
            	var data = new FormData();
				data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				profileService.cover(data).success(function(result) {
					if(result.code == 1){
						$scope.profileModel.displayCover = e.target.result;
					}
				}).error(function(error){
			    	sessionService.refresh(null);
			    });       	         	
            }
            reader.readAsDataURL(input.files[0]);  
	    }
	    
	    /**
	     * Modal d'information sur un badge
	     */
	    $ionicModal.fromTemplateUrl('templates/profile/modalBadge.html', {
	 	     scope: $scope
	 	   }).then(function(modal) {
	 	     $scope.modalBadge = modal;
	 	   });
	    
	    /**
	     * Ouverture de la modale
	     */
	    $scope.openBadge = function(badge){
	    	$scope.currentBadge  = badge;
	    	$scope.modalBadge.show();
	    }
	   
	    /**
	     * Modification en base de la visibilité du profil
	     */
	    $scope.toogleVisibility = function(){
	    	var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			profileService.visibility(data).error(function(error){
		    	sessionService.refresh($scope.toogleVisibility);
		    });
	    }

	});