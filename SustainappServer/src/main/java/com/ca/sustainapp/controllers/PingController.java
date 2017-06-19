package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Controller permettant de vérifier si l'application tourne toujours ou si elle est tombée
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/06/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PingController {
	private static final String ERR_MSG_EXCEPTION_TEST = "This is an exception in order to test the generic error screen";

	/**
	 * Test de l'existance de l'application
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ping", method = RequestMethod.GET, produces = SustainappConstantes.MIME_TEXT)
    public String ping(HttpServletRequest request){
		return "pong\n";
	}
	
	/**
	 * Test de l'envoi des erreurs
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/errors", method = RequestMethod.GET)
	public String error(HttpServletRequest request) {
		throw new RuntimeException (ERR_MSG_EXCEPTION_TEST);
	}
}
