package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.GregorianCalendar;
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
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.TeamsResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.TeamValidator;

/**
 * Restfull controller for reports management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class TeamController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private TeamServiceDAO teamService;
	@Autowired
	private TeamValidator validator;
	@Autowired
	private TeamRoleServiceDAO roleService;

	/**
	 * get all teams
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		if(!startIndex.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		SearchResult<TeamEntity> listResult = teamService.searchByCriteres(null, startIndex.get(), 20L);
		List<TeamEntity> teams = new SustainappList<TeamEntity>();
		for(TeamEntity team : listResult.getResults()){
			teams.add(team);
		}
		return new TeamsResponse().setTeams(teams).setCode(1).buildJson();
	}
	
	
	/**
	 * get a team by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * add a new team
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		TeamEntity team = new TeamEntity()
				.setName(request.getParameter("name"))
				.setLevel(0)
				.setTimestamps(GregorianCalendar.getInstance());
		if(!isEmpty(request.getParameter("file"))){
			team.setAvatar(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		}
		Long idTeam = teamService.createOrUpdate(team);
		TeamRoleEntity role = new TeamRoleEntity()
				.setProfilId(super.getConnectedUser(request).getProfile().getId())
				.setRole(SustainappConstantes.TEAMROLE_ADMIN)
				.setTeamId(idTeam)
				.setTimestamps(GregorianCalendar.getInstance());
		roleService.createOrUpdate(role);
		return new IdResponse().setId(idTeam).setCode(1).buildJson();
	}
	
	/**
	 * modify team informations
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", headers = "Content-Type= multipart/form-data", method = RequestMethod.PUT, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a team by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", method = RequestMethod.DELETE, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}

}