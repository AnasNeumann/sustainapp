/**
 * WS pour la gestion des profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
	 .factory('Service', function($cordovaCamera, $cordovaFile, $cordovaDevice) {
		 var buildOptions = function(quality, width, height){
			 return {
			      destinationType: Camera.DestinationType.DATA_URL,
			      allowEdit: true,
			      encodingType: Camera.EncodingType.JPEG,
			      quality : quality;
				  targetWidth : width;
				 options.targetHeight : height;
			      saveToPhotoAlbum: false
			    };
		 }
		 return {
			 getFromCamera = function(quality, width, height){
				 var option =  buildOptions(quality, width, height);
				 options.sourceType = Camera.PictureSourceType.CAMERA;
				 var result = null;
				 $cordovaCamera.getPicture(options).then(function(imageData) {
					 result = imageData;
				 }, function(err) {
					 console.log(err);
				 });
				 return result;
			 },
			 getFromGallery = function(quality, width, height){
				 var option =  buildOptions(quality, width, height);
				 options.sourceType = Camera.PictureSourceType.CAMERA;
				 var result = null;
				 $cordovaCamera.getPicture(options).then(function(imageData) {
					 result = imageData;
				 }, function(err) {
				      console.log(err);
				 });
				 return result;
			 }
		 };
	 });
 