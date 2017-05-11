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
			cityService.getById($stateParams.id).then(function(response){
				result = response.data;
				if(result.code == 1) {				
					$scope.title = result.city.name;
					$scope.model.city = result.city;
					$scope.model.cityTemp = result.city;
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
	
});