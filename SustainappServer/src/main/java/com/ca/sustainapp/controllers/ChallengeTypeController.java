package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.validators.ChallengeTypeValidator;

/**
 * Restfull controller for challenge type management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ChallengeTypeController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ChallengeTypeServiceDAO service;
	@Autowired
	private ChallengeTypeValidator validator;
	
	/**
	 * add a new challengeType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challengetype", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get all challengeType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challengetype/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a challengeType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challengetype/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
	
}