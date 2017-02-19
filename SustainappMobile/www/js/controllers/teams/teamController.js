/**
 * Controller for teams managments
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('teamController', function($scope, $stateParams, teamService, sessionService, teamRole, fileService) {
	
	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadTeam();
        });
	
	/**
	 * Chargement des informations sur la team
	 */
	var loadTeam = function(){
		$scope.teamModel = {};
		$scope.teamModel.loaded = false;
		$scope.teamModel.displayAvatar = "img/common/defaultAvatarMin.png";
		$scope.teamModel.file  = null;		
		teamService.getById($stateParams.id).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.teamModel.loaded = true;
				$scope.teamModel.owner  = result.owner;
				$scope.teamModel.members  = result.members;
				$scope.teamModel.requests  = result.requests;
				$scope.teamModel.participations = result.participations;
				$scope.teamModel.role = result.role;
				if(null != result.team.avatar){
					$scope.teamModel.displayAvatar = "data:image/jpeg;base64,"+ result.team.avatar;
				}
	   		}
    	});
	};
	
});
