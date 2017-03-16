package com.ca.sustainapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.ChallengeTypesResponse;
import com.ca.sustainapp.responses.ChallengesResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ChallengeValidator;

/**
 * Restfull controller for challenge management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017 <3
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ChallengeController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private ChallengeTypeServiceDAO challengeTypeService;
	@Autowired
	private ChallengeValidator validator;
	
	/**
	 * get all challenges types
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/types", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAllTypes(HttpServletRequest request) {
		return new ChallengeTypesResponse().setTypes(challengeTypeService.getAll()).setCode(1).buildJson();
	}
	
	/**
	 * get all challenges
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		if(!startIndex.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		SearchResult<ChallengeEntity> listResult = challengeService.searchByCriteres(null, startIndex.get(), 20L);
		List<ChallengeEntity> challenges = new SustainappList<ChallengeEntity>();
		for(ChallengeEntity challenge : listResult.getResults()){
			challenges.add(challenge);
		}
		return new ChallengesResponse().setChallenges(challenges).setCode(1).buildJson();
	}
	
	/**
	 * get a challenge by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * create a new challenge
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update challenge informations
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update challenge icon
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/icon", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String icon(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a challenge
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
	
}