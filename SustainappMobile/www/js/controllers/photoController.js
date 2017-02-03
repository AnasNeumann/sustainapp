/**
 * Essai de controller de communication avec l'appareil photo
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('PhotosCtrl', function($scope, $cordovaCamera) {
  
	/**
	 * Fonction de prise de photo et affichage
	 */
	$scope.takePicture = function() {	
		var options = {
	      quality: 75,
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
