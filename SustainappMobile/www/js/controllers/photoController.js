/**
 * Essai de controller de communication avec l'appareil photo
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('PhotosCtrl', function($scope, $cordovaCamera, $cordovaFile, $cordovaFileTransfer, $cordovaDevice) {

	
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
	              // Only copy because of access rights
	              $cordovaFile.copyFile(namePath, fileEntry.name, cordova.file.dataDirectory, newFileName).then(function(success){
	                $scope.image = newFileName;
	              }, function(error){
	                $scope.showAlert('Error', error.exception);
	              });
	            };
	          });

	    	$scope.imgURI = "data:image/jpeg;base64," + imageData;
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
	    }, function(err) {
	      console.log(err);
	    });
  };
  
});
