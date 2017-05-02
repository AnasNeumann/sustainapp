package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.websocket.NotificationSocket;

/**
 * Restfull controller pour l'affichage et ajout de notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class NotificationController {

	/**
	 * Recevoir toutes mes 20 dernières notifications et passer toutes les 0(reçue) à 1(vue)
	 * => mode classique sans websocket
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
	public String getAll(HttpServletRequest request){
		return null;
	}

	/**
	 * passer une notification de vue(1) à cliquée(2)
	 * => mode classique sans websocket
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification/read", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String read(HttpServletRequest request){
		return null;
	}

	/**
	 * Create a notification and send it to the profil
	 * => mode websocket
	 * @param message
	 * @param creator
	 * @param profil
	 * @param targetId
	 * @param targetType
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/notification/send")
    @SendTo("/notification/new")
	public NotificationSocket send(String message, ProfileEntity creator, ProfileEntity profil, Long targetId, Integer targetType) throws Exception {
		return new NotificationSocket();
	}
	
}