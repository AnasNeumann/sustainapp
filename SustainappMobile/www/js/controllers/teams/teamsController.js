/**
 * Controller for teams managments
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('teamsController', function($scope, $state, teamService, sessionService, teamRole, fileService, listService) {
	
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadAllTeams();
        });
	
	/**
	 * Chargement de toutes les teams
	 */
	var loadAllTeams = function(){
		$scope.teamsModel = {};
		$scope.teamsModel.moreTeams = true;
		$scope.teamsModel.startIndex = 0;
		$scope.teamsModel.add = false;
		$scope.teamsModel.emptyPicture = true;
		$scope.teamsModel.editFile = false;
		$scope.teamsModel.file = null;
		$scope.teamsModel.displayFile = "";
		$scope.teamsModel.name = "";
		$scope.teamsModel.allErrors = [];
		$scope.teamsModel.allTeams = [];
		$scope.getMoreTeam();
	};

	/**
	 * Modification de l'image de la nouvelle team
	 */
	$scope.chooseFile = function(newFile){
		fileService.getFile(newFile, 100, 600, 600).then(function(imageData) {
			$scope.teamsModel.file = imageData;
			$scope.teamsModel.displayFile = "data:image/jpeg;base64,"+imageData;
			$scope.teamsModel.emptyPicture = false;
			$scope.teamsModel.editFile = false;
		 }, function(err) {
		 });
	};
	
	/**
	 * Creation d'une nouvelle équipe
	 */
	$scope.createTeam = function(){
		var data = new FormData();
		if(null != $scope.teamsModel.file) {
			data.append("file", $scope.teamsModel.file);
		}		
		data.append("name", $scope.teamsModel.name);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		teamService.create(data).success(function(result) {
			if(result.code == 1){
				$scope.teamsModel.add = false;
				$scope.teamsModel.emptyPicture = true;
				$scope.teamsModel.editFile = false;
				$scope.teamsModel.file = null;
				$scope.teamsModel.displayFile = "";
				$scope.teamsModel.name = "";
				$scope.teamsModel.allErrors = [];
				$state.go('tab.team-one', {'id' : result.id});
			} else {
				$scope.teamsModel.allErrors = result.errors;
			}
		});
	};
	
	/**
	 * Recuperation de plus de teams [InfinityScroll]
	 */
	$scope.getMoreTeam = function(){
		$scope.teamsModel.load = true;
		teamService.getAll($scope.teamsModel.startIndex).then(function(response){
			result = response.data;
			$scope.teamsModel.load = false;
			$scope.$broadcast('scroll.infiniteScrollComplete');
			if(result.code == 1 && result.teams.length >0) {
				$scope.teamsModel.startIndex += result.teams.length;
				$scope.teamsModel.allTeams = listService.addWithoutDoublons($scope.teamsModel.allTeams, result.teams);
			} else {
				$scope.teamsModel.moreTeams = false;
			}
		});
	};
	
});