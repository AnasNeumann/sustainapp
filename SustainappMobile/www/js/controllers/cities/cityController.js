/**
 * Controller pour l'affichage d'une ville
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('cityController', 
		function($scope, $stateParams, $ionicModal, sessionService, displayService, cityService) {

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
			cityService.getById($stateParams.id).then(function(response){
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
					if(null != result.city.cover && "" != result.city.cover){
		    			 $scope.model.displayCover = "data:image/jpeg;base64,"+ result.city.cover;
		    		 }
				}
				$scope.model.isOwner = false;
				if(result.city.id == sessionService.getObject('city').id){
					$scope.model.isOwner = true;
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
	
});