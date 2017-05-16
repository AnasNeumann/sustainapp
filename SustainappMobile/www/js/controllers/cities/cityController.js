/**
 * Controller pour l'affichage d'une ville
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('cityController', 
		function($scope, $stateParams, $ionicModal, $cordovaGeolocation, sessionService, displayService, cityService, geolocationService, placeService) {

		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadCity();
        });
		
		/**
		 * Chargement de la ville pour affichage
		 */
		var loadCity = function(){
			$scope.model = {};
			$scope.model.loaded = false;
			$scope.title = "";
			$scope.model.allErrors = [];
			cityService.getById($stateParams.id, sessionService.get('id')).then(function(response){
				result = response.data;
				if(result.code == 1) {				
					$scope.title = result.city.name;
					$scope.model.city = result.city;
					$scope.model.cityTemp = result.city;
					$scope.model.name = result.city.name;
					$scope.model.about = result.city.about;
					$scope.model.phone = result.city.phone;
					$scope.model.website = result.city.website;
					$scope.model.displayCover = "";
					$scope.model.editCover = false;
					$scope.model.loaded = true;
					$scope.model.edit = false;
					$scope.model.newPlace = {};
					$scope.model.eltToDelete = {};
					$scope.model.newPlace.name = "";
					$scope.model.newPlace.about = "";
					$scope.model.newPlace.address = "";
					$scope.model.newPlace.longitude = "";
					$scope.model.newPlace.latitude = "";
					if(null != result.city.cover && "" != result.city.cover){
		    			 $scope.model.displayCover = "data:image/jpeg;base64,"+ result.city.cover;
		    		 }
					$scope.model.isOwner = result.owner;
				}
				
			});
			$scope._isNotMobile = displayService.isNotMobile;
		};

		/**
	     * update cover file [mobile mode]
	     */
	    $scope.cover = function(newFile){
	    	fileService.getFile(newFile, 100, 600, 240, false).then(function(imageData) {
	    		var data = new FormData();
				data.append("file", imageData);
				data.append("city", $stateParams.id);
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				cityService.cover(data).success(function(result) {
					if(result.code == 1){
						$scope.model.displayCover = "data:image/jpeg;base64,"+ imageData;
						$scope.model.editCover = false;
					}
				});
			 }, function(err) {
			 });
	    };

	    /**
	     * update cover file [desktop mode]
	     */
	    $scope.desktopCover = function(input){
	    	var reader = new FileReader();
            reader.onload = function (e) {
            	var data = new FormData();
            	data.append("city", $stateParams.id);
				data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
				data.append("sessionId", sessionService.get('id'));
				data.append("sessionToken", sessionService.get('token'));
				cityService.cover(data).success(function(result) {
					if(result.code == 1){
						$scope.model.displayCover = e.target.result;
					}
				});       	         	
            }
            reader.readAsDataURL(input.files[0]);  
	    };
	    
	    /**
	     * Modification en base de la ville
	     */
	    $scope.updateCity = function(){
	    	var data = new FormData();
			data.append("name", $scope.model.name);
			data.append("about", $scope.model.about);
			data.append("phone", $scope.model.phone);
			data.append("website", $scope.model.website);
			data.append("sessionId", sessionService.get('id'));
			data.append("city", $stateParams.id);
			data.append("sessionToken", sessionService.get('token'));
			cityService.update(data).success(function(result) {
				if(result.code == 1){
					$scope.model.edit = false;
					$scope.model.allErrors = [];
					$scope.model.city.name = $scope.model.name;
					$scope.model.city.about = $scope.model.about;
					$scope.model.city.phone = $scope.model.phone;
					$scope.model.city.website = $scope.model.website;
				}else{
					console.log(result);
					$scope.model.allErrors = result.errors;
				}
			});
	    };
	    
	   /**
	    * Modal de confirmation de la suppression d'un lieu
	    */
	   $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
	   });
	   
	   
	   /**
	    * Modal d'ajout d'un nouveau lieu
	    */
	   $ionicModal.fromTemplateUrl('templates/cities/modal-place.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modalPlace = modal;
	   });
	   
	   /**
	    * Localiser ma position actuelle
	    */
	   $scope.getCurrentPlace = function(){
			  var posOptions = {
					  timeout: 10000, 
					  enableHighAccuracy: false
			  };
			  $cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
				  $scope.model.newPlace.latitude = position.coords.latitude;
				  $scope.model.newPlace.longitude = position.coords.longitude;
			  }, function(err) {});
	   };
	   
	   /**
	    * Calculer la longitude et latitude à partir de l'addresse donnée
	    */
	   $scope.calcul = function(){
		   geolocationService.getLocationFromAddress($scope.model.newPlace.address).then(function(response){ 
			   var location = response.data.results[0].geometry.location;
			   $scope.model.newPlace.longitude = location.lng;
			   $scope.model.newPlace.latitude = location.lat;
		   });
	   };
	   
	   /**
	    * Ajouter une place en base de données
	    */
	   $scope.addPlace = function(){
		   var data = new FormData();
			data.append("name", $scope.model.newPlace.name);
			data.append("about", $scope.model.newPlace.about);
			data.append("longitude", $scope.model.newPlace.longitude);
			data.append("latitude", $scope.model.newPlace.latitude);
			data.append("address", $scope.model.newPlace.address);
			data.append("sessionId", sessionService.get('id'));
			data.append("city", $stateParams.id);
			data.append("sessionToken", sessionService.get('token'));
			placeService.create(data).success(function(result) {
				if(result.code == 1){
					var newPlace = {
						"id" : result.id,
						"address" : $scope.model.newPlace.address,
						"name" : $scope.model.newPlace.name
					};
					$scope.model.city.places.push(newPlace);
					$scope.model.newPlace.name = "";
					$scope.model.newPlace.about = "";
					$scope.model.newPlace.longitude = "";
					$scope.model.newPlace.latitude = "";
					$scope.model.newPlace.address = "";
					$scope.modalPlace.hide();
					$scope.model.newPlace.allErrors = [];
				} else {
					$scope.model.newPlace.allErrors = result.errors;
				}
			});
	   };
	   
	   /**
	    * Demande de confirmation pour la suppression d'un lieu 
	    */
	   $scope.deletePlace = function(place){
		   $scope.model.eltToDelete = place;
		   $scope.modal.show();
	   };
	   
		/**
		 * Confirmation de la suppression
		 */
		$scope.confirmDelete = function(){
			$scope.model.city.places.splice($scope.model.city.places.indexOf($scope.model.eltToDelete), 1);
			$scope.modal.hide();
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			data.append("place", $scope.model.eltToDelete.id);
			placeService.deleteById(data);
		};
	
});