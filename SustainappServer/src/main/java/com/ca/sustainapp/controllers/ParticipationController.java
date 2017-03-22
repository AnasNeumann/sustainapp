package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;

/**
 * Restfull controller for participation management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ParticipationController extends GenericController {

	/**
	 * DAO services
	 */
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private ParticipationServiceDAO participationService;
	
	/**
	 * create a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/create", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * create a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/update", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * vote for a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/vote", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String vote(HttpServletRequest request) {
		return null;
	}
	
}
