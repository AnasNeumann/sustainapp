/**
 * Fichier contenant toutes les service d'appel RESTFULL
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('starter.services', [])

	// Service de base d'ionic
	.factory('Chats', function() {
		// Might use a resource here that returns a JSON array

		// Some fake testing data
		var chats = [ {
			id : 0,
			name : 'Ben Sparrow',
			lastText : 'You on your way?',
			face : 'img/ben.png'
		}, {
			id : 1,
			name : 'Max Lynx',
			lastText : 'Hey, it\'s me',
			face : 'img/max.png'
		}, {
			id : 2,
			name : 'Adam Bradleyson',
			lastText : 'I should buy a boat',
			face : 'img/adam.jpg'
		}, {
			id : 3,
			name : 'Perry Governor',
			lastText : 'Look at my mukluks!',
			face : 'img/perry.png'
		}, {
			id : 4,
			name : 'Mike Harrington',
			lastText : 'This is wicked good ice cream.',
			face : 'img/mike.png'
		} ];

		return {
			all : function() {
				return chats;
			},
			remove : function(chat) {
				chats.splice(chats.indexOf(chat), 1);
			},
			get : function(chatId) {
				for (var i = 0; i < chats.length; i++) {
					if (chats[i].id === parseInt(chatId)) {
						return chats[i];
					}
				}
				return null;
			}
		};
	})

	// service d'appel d'un controller
	.factory('profilService', function($http) {
		return {
			allProfiles : function() {
				return $http.get("http://127.0.0.1:8085/profile/all");
			},
			search : function(query) {
				return $http.get("http://127.0.0.1:8085/profile?query=" + query);
			}
		};
	});