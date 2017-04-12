/**
 * service de traitement sur les images avec NG-CORDOVA
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
	 .factory('fileService', function($cordovaCamera, $cordovaFileTransfer, $cordovaFile, $cordovaDevice) {
		 var buildOptions = function(quality, width, height, allow){
			 return {
			      destinationType: Camera.DestinationType.DATA_URL,
			      allowEdit: allow,
			      encodingType: Camera.EncodingType.JPEG,
			      quality : quality,
				  targetWidth : width,
				  targetHeight : height,
			      saveToPhotoAlbum: false
			    };
		 };
		return {
			 getFile : function(newFile, quality, width, height, allow){
				 var options =  buildOptions(quality, width, height, allow);
				 if(true == newFile){
					 options.sourceType = Camera.PictureSourceType.CAMERA;
				 } else {
					 options.sourceType = Camera.PictureSourceType.PHOTOLIBRARY;
				 }
				 return $cordovaCamera.getPicture(options);
			 }
		 };
	 });
 