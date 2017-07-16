/**
 * Controller pour l'affichage d'un éco-lieu
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('placeController', 
		function($scope, $stateParams, $rootScope, $ionicModal, $cordovaGeolocation, fileService, sessionService, displayService, placeService) {

		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadPlace();
		});
		
		/**
		 * Chargement complet des informations du lieu
		 */
		var loadPlace = function(){
			$scope.model = {};
			$scope.model.loaded = false;
			$scope.model.allErrors = [];
			$scope.model.pictures = [];
			$scope.rating = {};
			$scope.rating.rate = 1;
			$scope.rating.max = 5;
			$scope.model.visited = false;
			$scope._isNotMobile = displayService.isNotMobile;
			placeService.getById($stateParams.id, sessionService.get('id')).then(function(response){
				result = response.data;
				if(result.code == 1) {
					$scope.model.loaded = true;
					$scope.model.average = result.average;
					$scope.model.pictures = result.pictures;
					$scope.model.place = result.place;
					$scope.model.name = result.place.name;
					$rootScope.$broadcast('TITLE', result.place.name);
					$scope.model.about = result.place.about;
					$scope.model.address = result.place.address;
					$scope.model.isOwner = result.isOwner;
					$scope.model.currentNote = result.note;
					$scope.model.nbrNotes = result.nbrNotes;
					$scope.model.edit = false;
					$scope.model.displayFile = "";
					$scope.model.file = null;
					$scope.model.editPicture = false;
					$scope.model.emptyPicture = true;
					$scope.model.newPicture = {};
					$scope.model.newPicture.name = "";
					$scope.model.newPicture.about = "";
					if(null != result.note){
						$scope.rating.rate = result.note.score;
					}
				}
			}, function(response){
				sessionService.refresh(loadPlace);
			});
		};
		
		/**
		 * Modification en base des informations d'un lieu
		 */
		$scope.updatePlace = function(){
			var data = new FormData();
			data.append("name", $scope.model.name);
			data.append("about", $scope.model.about);
			data.append("address", $scope.model.address);
			data.append("place", $stateParams.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			placeService.update(data).success(function(result) {
				if(result.code == 1){
					$scope.model.edit = false;
					$scope.model.allErrors = [];
					$scope.model.place.name = $scope.model.name;
					$scope.model.place.about = $scope.model.about;
					$scope.model.place.address = $scope.model.address;
				} else {
					$scope.model.allErrors = result.errors;
				}
			}).error(function(error){
		    	sessionService.refresh($scope.updatePlace);
		    });
		};

	   /**
	    * Modal d'ajout d'une nouvelle photo
	    */
	   $ionicModal.fromTemplateUrl('templates/cities/modal-picture.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modalPicture = modal;
	   });
	   
		/**
		 * Modification de la nouvelle image [mobile mode]
		 */
		$scope.chooseFile = function(newFile){
			fileService.getFile(newFile, 100, 700, 300, false).then(function(imageData) {
				$scope.model.displayFile = "data:image/jpeg;base64,"+imageData;
				$scope.model.file = imageData;
				$scope.model.editPicture = false;
				$scope.model.emptyPicture = false;				
			 }, function(err) {
			 });
		};
		
		/**
		 * Modification de la nouvelle image [desktop mode]
		 */
		$scope.desktopFile = function(input){
			var reader = new FileReader();
	        reader.onload = function (e) {
	        	$scope.$apply(function () {
	    			$scope.model.displayFile = e.target.result;
					$scope.model.file = e.target.result.substring(e.target.result.indexOf(",")+1);
					$scope.model.emptyPicture = false;
	    			
	            });           	         	
	        }
	        reader.readAsDataURL(input.files[0]); 
		};
		
		/**
		 * Ajout d'une nouvelle image en base
		 */
		$scope.addPicture = function(){
			if(null == $scope.model.file){
				return false;
			}
			var data = new FormData();
			data.append("name", $scope.model.newPicture.name);
			data.append("about", $scope.model.newPicture.about);
			data.append("file", $scope.model.file);
			data.append("place", $stateParams.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			placeService.addPicture(data).success(function(result) {
				if(result.code == 1){				
					var picture = {
							id : result.id,
							name : $scope.model.newPicture.name,
							about : $scope.model.newPicture.about,
							document : $scope.model.file
					};
					$scope.model.pictures.push(picture);
					$scope.model.allErrors = [];
					$scope.modalPicture.hide();
					$scope.model.newPicture.name = "";
					$scope.model.newPicture.about = "";
					$scope.model.displayFile = "";
					$scope.model.file = null;
					$scope.model.emptyPicture = true;
				} else {
					$scope.model.allErrors = result.errors;
				}
			}).error(function(error){
		    	sessionService.refresh($scope.addPicture);
		    });
		};
		
		/**
		 * Suppression en base d'une image
		 */
		$scope.deletePicture = function(picture){
			$scope.model.pictures.splice($scope.model.pictures.indexOf(picture), 1);
			var data = new FormData();
			data.append("picture", picture.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			placeService.delPicture(data).error(function(error){
		    	sessionService.refresh(null);
		    });
		};
		
	  /**
       * Modal de note pour le lieu
       */
       $ionicModal.fromTemplateUrl('templates/common/modalRank.html', {
 	     scope: $scope
 	   }).then(function(modal) {
 	     $scope.modalRank = modal;
 	   });
       
   	  /**
   	   * Méthode de notation d'un lieu
   	   */
   	   $scope.doRank = function(){
   		   $scope.modalRank.hide();
   		   var data = new FormData();
   		   data.append("score", $scope.rating.rate);
	   	   data.append("place", $stateParams.id);
	   	   data.append("sessionId", sessionService.get('id'));
	   	   data.append("sessionToken", sessionService.get('token'));
	   	   placeService.note(data).success(function(result){
	   		  if(result.code == 1){
	   			 $scope.model.nbrNotes = result.total;
	   			 $scope.model.average = result.average;
	   		  }
	   	   }).error(function(error){
		    	sessionService.refresh($scope.doRank);
		   });
   	  };

   	  /**
   	   * Verifier sa présence à un lieu
   	   */
   	  $scope.visit = function(){
   		 var posOptions = {
   			 timeout: 10000, 
   			 enableHighAccuracy: false
   	     };
   		 $cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
   		    var data = new FormData();
    	    data.append("lng", position.coords.longitude);
    	    data.append("lat", position.coords.latitude);
 	   	    data.append("place", $stateParams.id);
 	   	    data.append("sessionId", sessionService.get('id'));
 	   	    data.append("sessionToken", sessionService.get('token'));
 	   	    placeService.visit(data).success(function(result){
	   		  if(result.code == 1){
	   			$scope.model.visited = true;
	   		  }
	   	    }).error(function(error){
		    	sessionService.refresh($scope.visit);
		   });
   		 });
   	  };
   	
});