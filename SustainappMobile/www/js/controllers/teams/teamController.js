/**
 * Controller for teams managments
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('teamController', 
		function($scope, $stateParams, $ionicModal, $state, teamService, sessionService, teamRole, fileService, displayService) {
	
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
		$scope.title = "";
		$scope.teamModel = {};
		$scope.teamModel.loaded = false;
		$scope.teamModel.edit = false;
		$scope.teamModel.displayAvatar = "img/common/defaultAvatarMin.png";
		$scope.teamModel.avatarEdit = false;
		$scope.teamModel.file  = null;
		$scope.teamModel.allErrors = [];	
		teamService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {			
				$scope.title = result.team.name;
				$scope.teamModel.team  = result.team;
				$scope.teamModel.owner  = result.owner;
				$scope.teamModel.members  = result.members;
				$scope.teamModel.requests  = result.requests;
				$scope.teamModel.participations = result.participations;
				$scope.teamModel.role = result.role;
				$scope.teamModel.isAdmin = (result.role == teamRole.admin);
				$scope.teamModel.name = result.team.name;
				if(null != result.team.avatar){
					$scope.teamModel.displayAvatar = "data:image/jpeg;base64,"+ result.team.avatar;
				}
				getAvailableAction();
				$scope.teamModel.loaded = true;
	   		}
    	}, function(response){
    		sessionService.refresh(loadTeam);
    	});
		$scope._isNotMobile = displayService.isNotMobile;
	};
	
	/**
	 * rechercher l'action possible pour un utilisateur
	 */
	var getAvailableAction = function(){
		$scope.teamModel.displayAction = "action.apply";
		$scope.teamModel.action = "action.apply.accept";
		if(null != $scope.teamModel.role){
			switch($scope.teamModel.role){
				case teamRole.admin :
					$scope.teamModel.displayAction = "action.delete";
					$scope.teamModel.action = "action.delete";
					break;
				case teamRole.member :
					$scope.teamModel.displayAction = "action.leave";
					$scope.teamModel.action = "action.leave.cancel";
					break;
				case teamRole.request :
					$scope.teamModel.displayAction = "action.cancel";
					$scope.teamModel.action = "action.leave.cancel";
					break;
			}
		}
	};
	
	/**
	 * Modification du nom d'une équipe
	 */
	$scope.updateTeam = function(){
    	var data = new FormData();
		data.append("name", $scope.teamModel.name);
		data.append("team", $scope.teamModel.team.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		teamService.update(data).success(function(result) {
			if(result.code == 1){
				$scope.teamModel.allErrors = [];	
				$scope.teamModel.team.name = $scope.teamModel.name;
				$scope.teamModel.edit = false;
				$scope.teamModel.avatarEdit = false;
	    	} else {
	    		$scope.teamModel.allErrors = result.errors;
	    	}
	    }).error(function(error){
	    	sessionService.refresh($scope.updateTeam);
	    });
	};
	
	/**
	 * Modification de l'avatar d'une équipe [mobile mode]
	 */
	$scope.avatar = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {			
			var data = new FormData();
			data.append("file", imageData);
			data.append("team", $scope.teamModel.team.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			teamService.avatar(data).success(function(result) {
				if(result.code == 1){
					$scope.teamModel.file = imageData;
					$scope.teamModel.displayAvatar = "data:image/jpeg;base64,"+imageData;
					$scope.teamModel.avatarEdit = false;
		    	}
		    }).error(function(error){
		    	sessionService.refresh(null);
		    });
		 }, function(err) {
		 });		
	};
	
	/**
     * update avatar file [desktop mode]
     */
    $scope.desktopAvatar = function(input){
    	var reader = new FileReader();
        reader.onload = function (e) {
        	var data = new FormData();
			data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
			data.append("team", $scope.teamModel.team.id);
			data.append("sessionId", sessionService.get('id'));		
			data.append("sessionToken", sessionService.get('token'));
			teamService.avatar(data).success(function(result) {
				if(result.code == 1){
					$scope.teamModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
					$scope.teamModel.displayAvatar = e.target.result;
				}
			}).error(function(error){
		    	sessionService.refresh(null);
		    }); 
        }
        reader.readAsDataURL(input.files[0]);  
    }
	
	/**
	 * Handle role for a profile
	 */
	$scope.handleTeam = function(target, role, previous){
		var data = new FormData();
		data.append("team", $scope.teamModel.team.id);
		if("me" == previous){
			data.append("target", target);
			data.append("role", $scope.teamModel.action);
		}else{
			data.append("target", target.id);
			data.append("role", role);
		}
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));		
		teamService.handleRole(data).success(function(result) {
			if(result.code == 1){
				if("me" == previous){
					if($scope.teamModel.action == "action.apply.accept"){
						$scope.teamModel.displayAction = "action.cancel";
						$scope.teamModel.action = "action.leave.cancel";
						$scope.teamModel.role = "request";
					} else{
						$scope.teamModel.displayAction = "action.apply";
						$scope.teamModel.action = "action.apply.accept";
						$scope.teamModel.role = null;
						$scope.teamModel.members.splice(
								$scope.teamModel.members.indexOf(
										$scope.teamModel.members.filter(function(member){
												return member.id == target;
										})[0]), 1);
					}					
				} else if("member" == previous) {
					$scope.teamModel.members.splice($scope.teamModel.members.indexOf(target), 1);
				} else {
					$scope.teamModel.requests.splice($scope.teamModel.requests.indexOf(target), 1);
					if("action.apply.accept" == role){
						$scope.teamModel.members.push(target);    
					}
				}
	    	}
	    }).error(function(error){
	    	sessionService.refresh(null);
	    });
	};
	
   /**
    * Modal de confirmation de la suppression d'une team
    */
   $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
     scope: $scope
   }).then(function(modal) {
     $scope.modal = modal;
   });

	/**
	 * Suppression définitive d'une team
	 */
	$scope.confirmDelete = function(){
		$scope.modal.hide();
		var data = new FormData();
		data.append("team", $scope.teamModel.team.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		teamService.deleteById(data).success(function(result) {
			if(result.code == 1){
				$state.go('tab.teams');
	    	}
	    }).error(function(error){
	    	sessionService.refresh($scope.confirmDelete);
	    });
		return;
	};
});
