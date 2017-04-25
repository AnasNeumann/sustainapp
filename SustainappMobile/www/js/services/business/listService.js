/**
 * Service de traitements sur les listes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
	 .factory('listService', function() {
			return {
				
				/**
				 * Ajouter si l'objet n'existe pas déjà dans la liste
				 */
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
				 },
				 
				 /**
				  * Ajouter au début d'une liste
				  */
				 addOnTop : function(containerList, elt){
					 var result = [];
					 result.push(elt);				
					 for (i = 0; i < containerList.length; i++) {
						 result.push(containerList[i]);
					 }
					 return result;
				 },
				 
				 /**
				  * Mettre en liste en désordre aléatoirement
				  */
				 randomize : function(array) {
				    var result = array;
				 	for (var i = result.length - 1; i > 0; i--) {
				        var j = Math.floor(Math.random() * (i + 1));
				        var temp = result[i];
				        result[i] = result[j];
				        result[j] = temp;
				    }
				    return result;
				},		
				
				/**
				 * Reordoner une liste par numero
				 */
				reorder : function(array){
					array.sort(function(a,b) {return (a.numero > b.numero) ? 1 : ((b.numero > a.numero) ? -1 : 0);});
				}
			 };
		 });