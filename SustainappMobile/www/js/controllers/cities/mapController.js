/**
 * Controller pour l'affichage de la carte
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('mapController', function($scope, $cordovaGeolocation, placeService, geolocationService) {

	/**
	 * parameters for geolocation
	 */
	var posOptions = {
		 timeout: 10000, 
		 enableHighAccuracy: false
    };
	
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
		if(true != $scope.flag){
			loadMap();
		}else{
			reloadMap();
		}
    });
	
	/**
	 * Chargement de la carte dans la page
	 */
	var loadMap = function(){
		$scope.flag = true;
		$scope.model = {};
		$scope.model.loaded = false;
	    $cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
			var options = {
	            center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
	            zoom: 13,
	            disableDefaultUI: true    
	        }
	        $scope.map = new google.maps.Map(document.getElementById("map"), options);
		    $scope.model.loaded = true;
	    }, function(err) {});
	};
	
	/**
	 * Rechargement de la page les autres fois [recentrage sur la nouvelle position]
	 */
	var reloadMap = function(){
		$cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
			var newPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	        $scope.map.setCenter(newPos);
		}, function(err) {});
	};
	
});