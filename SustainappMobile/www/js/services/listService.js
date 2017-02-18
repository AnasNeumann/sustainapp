/**
 * Service de traitements sur les listes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
	 .factory('listService', function() {
			return {
				 addWithoutDoublons : function(targetList, containerList){
					 angular.forEach(containerList,function(containerObj,i){
						 var doublon = false;
						 angular.forEach(targetList,function(targetObj,j){
				                if(containerObj.id == targetObj.id){
				                	doublon = true;
				                }
				          });
				         if(!doublon){
				        	 targetList.push(containerObj);
				         }
			          });
					 return targetList;
				 }
			 };
		 });