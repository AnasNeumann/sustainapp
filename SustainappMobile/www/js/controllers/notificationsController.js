/**
 * Essai de controller de communication avec l'appareil photo
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('notificationsController', function($scope, $cordovaCamera, $http, $cordovaFile, $cordovaFileTransfer, $cordovaDevice) {

	$scope.model = {};
	
	/**
	 * Fonction de prise de photo et affichage
	 */
	$scope.loadPicture = function() {	
		var options = {
	      quality: 100,
	      destinationType: Camera.DestinationType.DATA_URL,
	      sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
	      allowEdit: true,
	      encodingType: Camera.EncodingType.JPEG,
	      targetWidth: 300,
	      targetHeight: 300,
	      saveToPhotoAlbum: false
	    };
	    $cordovaCamera.getPicture(options).then(function(imageData) {
	        window.FilePath.resolveNativePath(imageData, function(entry) {
	            window.resolveLocalFileSystemURL(entry, success, fail);
	            function fail(e) {
	              console.error('Error: ', e);
	            }
	            function success(fileEntry) {
	                var namePath = fileEntry.nativeURL.substr(0, fileEntry.nativeURL.lastIndexOf('/') + 1);
	                $cordovaFile.copyFile(namePath, fileEntry.name, cordova.file.dataDirectory, newFileName).then(function(success){
	                $scope.image = newFileName;
	              }, function(error){
	                $scope.showAlert('Error', error.exception);
	              });
	            };
	          });
	    	$scope.imgURI = "data:image/jpeg;base64," + imageData;
	    	$scope.model.picData = imageData;
	    }, function(err) {
	      console.log(err);
	    });
  };

	/**
	 * Fonction de prise de photo et affichage
	 */
	$scope.takePicture = function(type) {	
		var options = {
	      quality: 100,
	      destinationType: Camera.DestinationType.DATA_URL,
	      sourceType: Camera.PictureSourceType.CAMERA,
	      allowEdit: true,
	      encodingType: Camera.EncodingType.JPEG,
	      targetWidth: 300,
	      targetHeight: 300,
	      saveToPhotoAlbum: false
	    };
	    $cordovaCamera.getPicture(options).then(function(imageData) {
	      $scope.imgURI = "data:image/jpeg;base64," + imageData;
	      $scope.model.picData = imageData;
	      $scope.uploadFile();
	    }, function(err) {
	      console.log(err);
	    });
  };
  
  /**
   * Autre mode d'envoi post de l'image
   */
  $scope.uploadFile = function() {
	    var fd = new FormData();
	    fd.append("file", $scope.model.picData);
	    $http.post("http://192.168.43.195:8085/profile/upload", fd, {
	        withCredentials: true,
	        headers: {
	        	'Content-Type': undefined
            },
	        transformRequest: angular.identity
	    }).success(function(result) {
			  alert("SUCCESS: " + JSON.stringify(result));
	      }).error(function(err) {
	    	  alert("ERROR: " + JSON.stringify(err));
	    	  alert(fd);
	      });
	};
  
});
