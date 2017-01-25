package com.ca.sustainapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2017
 * @version 1.0
 */
@RestController
public class ProfileController {
	
	/**
	 * get all profiles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get() {
        return SustainappConstantes.SUCCES_JSON;
    }
	
}
