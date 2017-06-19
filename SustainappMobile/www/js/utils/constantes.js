/**
 * Fichier contenant toutes les constantes du projet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('sustainapp.constantes')
	.constant('config', { 
		remoteServer : 'https://www.uprodit.com/sustainappws' //uprodit server
		//remoteServer : 'http://192.168.43.195:8085'  //note4 modem
	    //remoteServer : 'http://127.0.0.1:8085' //localhost
	})
	.constant('teamRole', {
	    admin : "admin",
		request : "request",
		member : "member"
	});