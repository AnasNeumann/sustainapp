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
public class EssaiController {
	
	/**
	 * Juste pour essayer le MVC Restfull
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/essai", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get() {
        return SustainappConstantes.SUCCES_JSON;
    }
	
	/**
	 * Juste pour essayer le MVC Restfull
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/essai", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String post() {
        return SustainappConstantes.SUCCES_JSON;
    }
	
	/**
	 * Juste pour essayer le MVC Restfull
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/essai", method = RequestMethod.PUT, produces = SustainappConstantes.MIME_JSON)
    public String put() {
        return SustainappConstantes.SUCCES_JSON;
    }
	
	/**
	 * Juste pour essayer le MVC Restfull
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/essai", method = RequestMethod.DELETE, produces = SustainappConstantes.MIME_JSON)
    public String delete() {
        return SustainappConstantes.SUCCES_JSON;
    }
}