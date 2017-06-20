/**
 * Controller pour l'affichage de la carte
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('mapController', function($scope, $cordovaGeolocation, $state, placeService, geolocationService) {

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
			initMap();
		}else{
			reloadMap();
		}	
    });

	/**
	 * Initialisation de la map
	 */
	var initMap = function(){
		$scope.model = {};
		$scope.model.markers = [];
		$scope.flag = true;
		loadMap();
	}

	/**
	 * Chargement de la carte dans la page
	 */
	var loadMap = function(){
		$scope.model.loaded = false;
	    $cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
	    	var options = {
	            center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
	            zoom: 13,
	            disableDefaultUI: true    
	        }
	        $scope.map = new google.maps.Map(document.getElementById("map"), options);
		    $scope.model.loaded = true;
		    reloadPlaces(position.coords.longitude, position.coords.latitude);
	    }, function(err) {
	    	alert($filter('translate')("error.position"));
	    });
	};

	/**
	 * Récupération des éco-lieux proches
	 */
	var reloadPlaces = function(lng ,lat){	
		placeService.getNear(lng, lat).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				var infowindow = new google.maps.InfoWindow({
		          content: $scope.content
		        });
				for(var i in result.places){
					var place = result.places[i];
					var position = {lat: place.latitude, lng: place.longitude};
					var marker = new google.maps.Marker({
					    position: position,
					    map: $scope.map,
					    animation: google.maps.Animation.DROP,
					    title: place.name,
				    	label: {
				    	    color: 'black',
				    	    fontWeight: 'bold',
				    	    text: place.name,
				    	},
				        icon: {
				    	    labelOrigin: new google.maps.Point(45, 100),
				    	    url: "img/map/markerMin.png",
				    	},
				    	id : place.id
					  });
					marker.addListener('click', function() {
						$state.go('tab.place', {"id":this.id});
			        });
					$scope.model.markers.push(marker);
				}
			}
		});
	};

	/**
	 * Vider la map des précédents markeurs
	 */
	var reloadMap = function(){	
		$scope.model.loaded = false;
		for(var i in $scope.model.markers){
			$scope.model.markers[i].setMap(null);
		}
		$scope.model.markers = [];
	    $cordovaGeolocation.getCurrentPosition(posOptions).then(function(position) {
		    $scope.model.loaded = true;
		    $scope.map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
		    reloadPlaces(position.coords.longitude, position.coords.latitude);
	    }, function(err) {
	    	alert($filter('translate')("error.position"));
	    });
	};
});