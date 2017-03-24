package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.SearchResponse;

/**
 * Restfull controller for profiles and teams research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class SearchController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private TeamServiceDAO teamService;
	
	/**
	 * search profiles and teams
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String search(HttpServletRequest request) {
		String query = request.getParameter("query");
		if(isEmpty(query)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new SearchResponse()
				.setProfiles(profileService.searchByFulName(query,5))
				.setTeams(teamService.searchByKeywords(query, 5))
				.setCode(1)
				.buildJson();
	}

}