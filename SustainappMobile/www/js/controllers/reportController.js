/**
 * Controller de la visualisation/modification d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('reportController', 
			function($scope, $cordovaFile, $cordovaFileTransfer, $cordovaDevice, sessionService, fileService, reportService, displayService) {
		
		/**
		 * Initialisation du model
		 */
		var initPage = function(){
			$scope.reportModel = {};
			$scope.reportModel.message = "";
			$scope.reportModel.file = null;
			$scope.reportModel.displayFile = null;
			$scope.reportModel.sended = false;
			$scope.reportModel.emptyFile = true;
			$scope.reportModel.editFile = false;
			$scope.reportModel.allErrors = [];
			$scope._isNotMobile = displayService.isNotMobile;
		};
		initPage();
		
		/**
		 * Choix d'une photo a envoyer en mode mobile
		 */
		$scope.chooseFile = function(newFile){
			fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {
				$scope.reportModel.file = imageData;
				$scope.reportModel.displayFile = "data:image/jpeg;base64,"+imageData;
				$scope.reportModel.emptyFile = false;
				$scope.reportModel.editFile = false;
			 }, function(err) {
			 });
		}
		
		/**
		 * Choix d'une photo en mode desktop
		 */
		$scope.desktopFile = function(input){
			var reader = new FileReader();
            reader.onload = function (e) {
            	$scope.$apply(function () {
            		$scope.reportModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
                	$scope.reportModel.displayFile = e.target.result;
                	$scope.reportModel.emptyFile = false;   
                });           	         	
            }
            reader.readAsDataURL(input.files[0]);  
		}
		
		/**
		 * Envoi du signal pour l'administration
		 */
		$scope.signal = function(){
			var data = new FormData();
			data.append("file", $scope.reportModel.file);
			data.append("message", $scope.reportModel.message);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			reportService.create(data).success(function(result) {
				if(result.code == 1){
					$scope.reportModel.displayFile = null;
					$scope.reportModel.file = null;
					$scope.reportModel.message = "";
					$scope.reportModel.emptyFile = true;
					$scope.reportModel.sended = true;
					$scope.reportModel.allErrors = [];
				} else {
					$scope.reportModel.allErrors = result.errors;
				}
			}).error(function(error){
		    	sessionService.refresh($scope.signal);
		    });
		}

	});