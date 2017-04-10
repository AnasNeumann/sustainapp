/**
 * Service de récupération des différents types de question disponibles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/04/2017
 * @version 1.0
 */
angular.module('sustainapp.services')
	 .factory('questionTypeService', function() {
		  var baseURL = "img/questions/";
		  return {
				 getAll : function(){					
					 return [
						 {
							"code" : 0,
							"name" : "questionType.quiz",
							"pictureURL" : baseURL+"quiz.png"
						 },
						 {
							 "code" : 1,
							 "name" : "questionType.selection",
							 "pictureURL" : baseURL+"selection.png"
						 },
						 {
							 "code" : 2,
							 "name" : "questionType.classification",
							 "pictureURL" : baseURL+"classification.png"
						 },
						 {
							 "code" : 3,
							 "name" : "questionType.reorder",
							 "pictureURL" : baseURL+"reorder.png"
						 }
					 ];
				 }
			 };
		 });