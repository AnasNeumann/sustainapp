package com.ca.sustainapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Essai de mise en place d'un controller RESTfull
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 *
 */	
@RestController
@RequestMapping(value="/")
public class EssaiController {
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/essai", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String index() {
		System.out.println("i am here");
        return "Greetings from Spring Boot!";
    }
}