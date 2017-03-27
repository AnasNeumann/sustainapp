/**
 * Fichier contenant toutes les constantes du projet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/02/2017
 * @version 1.0
 */
angular.module('sustainapp.constantes')
	.constant('config', { 
		//remoteServer : 'http://10.244.66.9:8085' // ip valid only for laval open wifi connection
		//remoteServer : 'http://192.168.43.195:8085'  // ip valid only for samsung note4 4g connection 
	    remoteServer : 'http://127.0.0.1:8085' //ip valid only with simulator device
	})
	.constant('teamRole', {
	    admin : "admin",
		request : "request",
		member : "member"
	});