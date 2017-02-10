/**
 * Essai de controller de databinding
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/02/2017
 * @version 1.0
 */
angular.module('sustainapp.controllers')
.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
 
	$scope.chat = Chats.get($stateParams.chatId);
});