package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

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
	 * Recevoir toutes en infinityscroll dernières notifications et passer toutes les 0(reçue) à 1(vue)
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
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification/read", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String read(HttpServletRequest request){
		return null;
	}

	/**
	 * Récuperer les notifications qui sont a reçue(0)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
	public String getUnread(HttpServletRequest request){
		return null;
	}
	
}