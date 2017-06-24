/**
 * Controller des signalements
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/05/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
	.controller('reportsController', function($scope, sessionService, listService, reportService) {
	
		/**
		 * Entrée dans la page
		 */
		$scope.$on('$ionicView.beforeEnter', function() {
			loadModel();
        });
		
		/**
		 * Chargement initial du model
		 */
		var loadModel = function(){
			$scope.reportModel = {};
			$scope.reportModel.moreReports = true;
			$scope.reportModel.startIndex = 0;
			$scope.reportModel.reports = [];
			$scope.getMoreReports();
		};
		
		/**
		 * Chargement infinity scroll de plus de signalement
		 */
		$scope.getMoreReports = function(){
			$scope.reportModel.load = true;
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			data.append("startIndex", $scope.reportModel.startIndex);
			reportService.getAll(data).success(function(result) {
				$scope.reportModel.load = false;
				$scope.$broadcast('scroll.infiniteScrollComplete');
				if(result.code == 1 && result.reports.length >0) {
					$scope.reportModel.startIndex += 1;
					$scope.reportModel.reports = listService.addWithoutDoublons($scope.reportModel.reports, result.reports);
				} else {
					$scope.reportModel.moreReports = false;
				}
		    }).error(function(error){
		    	sessionService.refresh($scope.getMoreReports);
		    });
		};
		
		/**
		 * Considérer un signalement comme étant traité
		 */
		$scope.done = function(report){		
			var data = new FormData();
			data.append("sessionId", sessionService.get('id'));
			data.append("sessionToken", sessionService.get('token'));
			data.append("report", report.id);
			reportService.update(data).success(function(response){
				$scope.reportModel.reports.splice($scope.reportModel.reports.indexOf(report), 1);
			}).error(function(error){
		    	sessionService.refresh($scope.done);
		    });
		};

	});