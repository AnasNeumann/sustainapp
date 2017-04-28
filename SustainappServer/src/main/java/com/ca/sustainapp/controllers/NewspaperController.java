package com.ca.sustainapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.responses.LeaderCoursResponse;
import com.ca.sustainapp.responses.LeaderParticipationResponse;
import com.ca.sustainapp.responses.NewspaperResponse;

/**
 * Restfull controller pour l'affichage du journal
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class NewspaperController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	
	
	/**
	 * Recevoir toutes les informations pour l'affichage du newspaper
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/newspaper", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getNewspaper(HttpServletRequest request) {
		return new NewspaperResponse()
				.setCours(getLeaderCourses())
				.setParticipations(getLeaderParticipations())
				.setCode(1)
				.buildJson();
	}
	
	/**
 	 * Recupérer la liste des 3 meilleurs cours
	 * @return
	 */
	private List<LeaderCoursResponse> getLeaderCourses(){
		return null;
	}
	
	/**
	 * Récuperer la liste des 3 meilleures participations
	 * @return
	 */
	private List<LeaderParticipationResponse> getLeaderParticipations(){
		return null;
	}
	
}