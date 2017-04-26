/**
 * Controller pour l'affichage principal d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('coursController', 
			function($scope, $ionicPopover, $stateParams, $state, $ionicModal, sessionService, coursService, topicService, fileService, listService, displayService) {

	/**
	 * Entrée dans la page
	 */
	$scope.$on('$ionicView.beforeEnter', function() {
			loadCours();
        });
		
	/**
	 * Chargement des informations du cours
	 */
	var loadCours = function(){
		$scope.coursModel = {};
		$scope.coursModel.loaded = false;
		$scope.title = "...";

		$scope.coursModel.emptyFile = true;
		$scope.coursModel.edit = false;
		$scope.coursModel.pictureEdit = false;
		$scope.coursModel.file  = null;
		
	    $scope.rating = {};
		$scope.rating.rate = 1;
		$scope.rating.max = 5;
		
		$scope.deleteType = true;
		$scope.eltToDelete  = {};
		
		$scope.coursModel.reorder = false;
		
		$scope.coursModel.topic = {};
		$scope.coursModel.topic.title = "";
		$scope.coursModel.topic.about = "";
		$scope.coursModel.editTopicFile = false;
		$scope.coursModel.emptyTopicFile = true;
		$scope.coursModel.displayTopicFile = "";
		$scope.coursModel.topicFile = null;
		$scope.coursModel.allTopicErrors = [];
		
		$ionicPopover.fromTemplateUrl('templates/common/popover-level.html', {
		    scope: $scope
		  }).then(function(popover) {
		    $scope.levels = [0,1,2,3,4,5,6,7,8,9];
		    $scope.popoverLevel = popover;
		  });

		$scope._isNotMobile = displayService.isNotMobile;
		$scope.coursModel.allErrors = [];
		coursService.getById($stateParams.id, sessionService.get('id')).then(function(response){
			var result = response.data;
			if(result.code == 1) {
				$scope.coursModel.loaded = true;
				$scope.coursModel.cours = result.cours;
				$scope.title = result.cours.title;
				$scope.coursModel.isAdmin = result.isOwner;
				$scope.coursModel.owner = result.owner;
				$scope.coursModel.hasLevel = result.hasLevel;
				$scope.coursModel.averageRank = result.averageRank;				
				$scope.coursModel.totalRank = $scope.coursModel.cours.listRank.length;
				if(null != result.rank){
					$scope.rating.rate = result.rank.score;
				}				
				$scope.coursModel.topics = result.topics;
				$scope.coursModel.displayPicture = "img/common/defaultCours.png";
				$scope.coursModel.title = result.cours.title;
				$scope.coursModel.about = result.cours.about;
				$scope.coursModel.open = ($scope.coursModel.cours.open != 0);
				if(null != result.cours.picture){
					$scope.coursModel.displayPicture = "data:image/jpeg;base64,"+ result.cours.picture;
				}
			}
		});
	};
	
	/**
	 * Méthode de notation d'un cours
	 */
	$scope.doRank = function(){
		$scope.modalRank.hide();
		var data = new FormData();
		data.append("score", $scope.rating.rate);
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.note(data).success(function(result) {
			if(result.code == 1){
				$scope.coursModel.totalRank = result.total;
				$scope.coursModel.averageRank = result.average;
			}
		});
	}
	
	/**
	 * fonction d'ouverture du menu de choix du type de challenge
	 */
	$scope.openLevelMin = function($event){
		$scope.popoverLevel.show($event);
	}
	
	/**
	 * fonction de changement du level minimum
	 */
	$scope.changeLevelMin = function(level){
		$scope.popoverLevel.hide();
		$scope.coursModel.cours.levelMin = level;
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("level", $scope.coursModel.cours.levelMin);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.level(data);
	}
	
	/**
     * Modal de confirmation de la suppression d'un cours ou topic
     */
     $ionicModal.fromTemplateUrl('templates/common/modalDelete.html', {
	     scope: $scope
	   }).then(function(modal) {
	     $scope.modal = modal;
	   });
     
     /**
      * Modal de note pour le cours
      */
      $ionicModal.fromTemplateUrl('templates/common/modalRank.html', {
 	     scope: $scope
 	   }).then(function(modal) {
 	     $scope.modalRank = modal;
 	   });
      
      /**
       * Modal pour l'ajout d'un topic
       */
       $ionicModal.fromTemplateUrl('templates/certificates/modalTopic.html', {
  	     scope: $scope
  	   }).then(function(modal) {
  	     $scope.modalTopic = modal;
  	   });
	
	/**
	 * Modification de l'image du cours [desktop mode]
	 */
	$scope.desktopPicture = function(input){
		var reader = new FileReader();
        reader.onload = function (e) {
        	var data = new FormData();
			data.append("file", e.target.result.substring(e.target.result.indexOf(",")+1));
			data.append("cours", $scope.coursModel.cours.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			coursService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.coursModel.file = e.target.result.substring(e.target.result.indexOf(",")+1);
					$scope.coursModel.displayPicture = e.target.result;
				}
			});
        }
        reader.readAsDataURL(input.files[0]);  
	}
	
	/**
	 * Modification des informations d'un cours
	 */
	$scope.updateCours = function(){
		var data = new FormData();
		data.append("title", $scope.coursModel.title);
		data.append("about", $scope.coursModel.about);
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.update(data).success(function(result) {
			if(result.code == 1){
				$scope.coursModel.allErrors = [];	
				$scope.coursModel.cours.title = $scope.coursModel.title;
				$scope.coursModel.cours.about = $scope.coursModel.about;
				$scope.coursModel.edit = false;
	    	} else {
	    		$scope.coursModel.allErrors = result.errors;
	    	}
		});
	}
	
	/**
	 * Modification de l'ouverture fermeture d'un cours
	 */
	$scope.toogleOpen = function(){
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.toogleOpen(data);
	}
	
	/**
	 * Modification de l'image du cours [mobile mode]
	 */
	$scope.picture = function(newFile){
		fileService.getFile(newFile, 100, 600, 600, true).then(function(imageData) {			
			var data = new FormData();
			data.append("file", imageData);
			data.append("cours", $scope.coursModel.cours.id);
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			coursService.picture(data).success(function(result) {
				if(result.code == 1){
					$scope.coursModel.file = imageData;
					$scope.coursModel.displayPicture = "data:image/jpeg;base64,"+imageData;
					$scope.coursModel.iconEdit = false;
		    	}
		    });
		 }, function(err) {
		 });
	}

   /**
    * demande de confirmation de la suppression du cours
    */
    $scope.confirmDeleteCours = function(){
	   $scope.deleteType = true;
	   $scope.modal.show();
	}
   
   /**
    * demande de confirmation de la suppression d'un topic
    */
    $scope.confirmDeleteTopic = function(elt){
	   $scope.deleteType = false;
	   $scope.eltToDelete  = elt;
	   $scope.modal.show();
	}
    
    /**
   	 * Suppression définitive d'un cours ou d'un topic
   	 */
   	$scope.confirmDelete = function(){
   		$scope.modal.hide();
   		if($scope.deleteType){
   			deleteCours();
   		} else {
   			deleteTopic();
   		}
   	};
   	
   	/**
	 * Suppression en base d'un cours
	 */
	var deleteCours = function(){
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		coursService.deleteById(data).success(function(result) {
			if(result.code == 1){
				$state.go('tab.certificates');
	    	}
	    });
		return;
	}
	
	/**
	 * Suppression en base d'un topic
	 */
	var deleteTopic = function(){
		$scope.coursModel.topics.splice($scope.coursModel.topics.indexOf($scope.eltToDelete), 1);
		var data = new FormData();
		data.append("topic", $scope.eltToDelete.topic.id);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		topicService.deleteById(data);
	}
	
	/**
	 * Ajout d'une image pour un topic [mobile mode]
	 */
	$scope.chooseTopicFile = function(newFile){
		fileService.getFile(newFile, 100, 700, 300, false).then(function(imageData) {
			$scope.coursModel.editTopicFile = false;
			$scope.coursModel.emptyTopicFile = false;
			$scope.coursModel.displayTopicFile = "data:image/jpeg;base64,"+imageData;
			$scope.coursModel.topicFile = imageData;			
		 }, function(err) {
		 });
	}
	
	/**
	 * Ajout d'une image pour un topic [desktop mode]
	 */
	$scope.desktopTopicFile = function(input){
		var reader = new FileReader();
        reader.onload = function (e) {
        	$scope.$apply(function () {
        		$scope.coursModel.topicFile =  e.target.result.substring(e.target.result.indexOf(",")+1);
        		$scope.coursModel.displayTopicFile = e.target.result;
        		$scope.coursModel.emptyTopicFile = false;
            });           	         	
        }
        reader.readAsDataURL(input.files[0]); 
	}
	
	/**
	 * Création d'un nouveau topic
	 */
	$scope.createTopic = function(){
		var data = new FormData();
		data.append("cours", $scope.coursModel.cours.id);
		data.append("title", $scope.coursModel.topic.title);
		data.append("about", $scope.coursModel.topic.about);
		data.append("sessionId", sessionService.get('id'));
		data.append("sessionToken", sessionService.get('token'));
		if(null != $scope.coursModel.topicFile) {
			data.append("file", $scope.coursModel.topicFile);
		}
		topicService.create(data).success(function(result) {
			if(result.code == 1){								
				var newTopic = {
						"topic" : {
							"id"    : result.id,
							"title" : $scope.coursModel.topic.title,
							"content" : $scope.coursModel.topic.about
						},
						"done" : false
				};
				$scope.coursModel.topics.push(newTopic);
				$scope.coursModel.topic.title = "";
				$scope.coursModel.topic.about = "";
				$scope.coursModel.editTopicFile = false;
				$scope.coursModel.emptyTopicFile = true;
				$scope.coursModel.displayTopicFile = "";
				$scope.coursModel.topicFile = null;
				$scope.coursModel.allTopicErrors = [];
				$scope.modalTopic.hide();
	    	} else {
	    		$scope.coursModel.allTopicErrors = result.errors;
	    	}
	    });
	}
	
	/**
	 * Reorder topics
	 */
	 $scope.moveTopic = function(elt, fromIndex, toIndex) {
		 $scope.coursModel.topics.splice(fromIndex, 1);
		 $scope.coursModel.topics.splice(toIndex, 0, elt);
		 var data = new FormData();
		 data.append("topic", elt.topic.id);
		 data.append("position", toIndex);
		 data.append("sessionId", sessionService.get('id'));
		 data.append("sessionToken", sessionService.get('token'));
		 topicService.drop(data);
	  };
	
});