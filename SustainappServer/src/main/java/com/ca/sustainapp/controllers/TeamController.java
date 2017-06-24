package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.TeamResponse;
import com.ca.sustainapp.responses.TeamsResponse;
import com.ca.sustainapp.services.CascadeDeleteService;
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
	private TeamRoleServiceDAO roleService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private CascadeDeleteService deleteService;

	
	@Autowired
	private TeamValidator validator;
	
	
	/**
	 * add a new team
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
		}
		TeamEntity team = new TeamEntity()
				.setName(request.getParameter("name"))
				.setLevel(0)
				.setTimestamps(GregorianCalendar.getInstance());
		if(!isEmpty(request.getParameter("file"))){
			team.setAvatar(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		}
		Long idTeam = teamService.createOrUpdate(team);
		TeamRoleEntity role = new TeamRoleEntity()
				.setProfilId(super.getUser(request).getProfile().getId())
				.setRole(SustainappConstantes.TEAMROLE_ADMIN)
				.setTeamId(idTeam)
				.setTimestamps(GregorianCalendar.getInstance());
		roleService.createOrUpdate(role);
		return super.success(new IdResponse().setId(idTeam));
	}
	
	/**
	 * get all teams
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		if(!startIndex.isPresent()){
			return super.refuse();
		}
		SearchResult<TeamEntity> listResult = teamService.searchByCriteres(null, startIndex.get(), 20L);
		List<TeamEntity> teams = new SustainappList<TeamEntity>();
		for(TeamEntity team : listResult.getResults()){
			teams.add(team);
		}
		return super.success(new TeamsResponse().setTeams(teams));
	}	
	
	/**
	 * get a team by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("team"));
		if(!id.isPresent()){
			return super.refuse();
		}
		TeamResponse response = new TeamResponse()
				.setTeam(teamService.getById(id.get()));
		if(null == response.getTeam()){
			return super.refuse();
		}
		return super.success(response
			.setRequests(getProfileByRole(response.getTeam(), SustainappConstantes.TEAMROLE_REQUEST))
			.setMembers(getProfileByRole(response.getTeam(), SustainappConstantes.TEAMROLE_MEMBER))
			.setOwner(getProfileByRole(response.getTeam(), SustainappConstantes.TEAMROLE_ADMIN).get(0))
			.setRole(searchRole(response.getTeam(), request))
			.setParticipations(null));
	}

	/**
	 * modify team informations
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		String name = request.getParameter("name");
		TeamEntity team = verifyAllOwnerInformations(request);
		if(null == team || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
		}
		teamService.createOrUpdate(team.setName(name));
		return super.success();
	}

	/**
	 * modify team avatar
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/avatar", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> avatar(HttpServletRequest request) {
		TeamEntity team = verifyAllOwnerInformations(request);
		if(null == team){
			return super.refuse();
		}
		teamService.createOrUpdate(team.setAvatar(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG)));
		return super.success();
	}

	/**
	 * delete a team by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		TeamEntity team = verifyAllOwnerInformations(request);
		if(null == team){
			return super.refuse();
		}
		deleteService.cascadeDelete(team);
		return super.success();
	}

	/**
	 * modify team role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/team/role", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> handleRole(HttpServletRequest request) {
		Optional<Long> teamId = StringsUtils.parseLongQuickly(request.getParameter("team"));
		Optional<Long> targetId = StringsUtils.parseLongQuickly(request.getParameter("target"));
		UserAccountEntity user = super.getConnectedUser(request);
		String role = request.getParameter("role");
		if(!teamId.isPresent() || !targetId.isPresent() || null == user || isEmpty(role)){
			return super.refuse();
		}
		TeamEntity team = teamService.getById(teamId.get());
		ProfileEntity profile = profileService.getById(targetId.get());
		if(null == team || null == profile){
			return super.refuse();
		}
		return super.send(new HttpRESTfullResponse(), isCorrectAction(team, profile, role, user)? 1 : 0);
	}

	/**
	 * Verifier les droits d'action et agir sur les teamRoles
	 * @param team
	 * @param profile
	 * @param role
	 * @param user
	 * @return
	 */
	private Boolean isCorrectAction(TeamEntity team, ProfileEntity profile, String role, UserAccountEntity user){
		TeamRoleEntity currentRole = this.searchTeamRoleProfile(team, profile);
		switch(role){
			case SustainappConstantes.TEAMROLE_CANCEL_OR_LEAVE :
				if(user.getProfile().getId().equals(profile.getId()) && null != currentRole){
					roleService.delete(currentRole.getId());
					return true;
				}
				break;
			case SustainappConstantes.TEAMROLE_FIRE_OR_REFUSE : 
				if(isOwnerTeam(team, user.getProfile()) && null != currentRole){
					roleService.delete(currentRole.getId());
					return true;
				}
				break;
			case SustainappConstantes.TEAMROLE_REQUEST_OR_ACCEPT :			
				if(user.getProfile().getId().equals(profile.getId()) && null == currentRole){
					roleService.createOrUpdate(new TeamRoleEntity()
							.setProfilId(profile.getId())
							.setRole(SustainappConstantes.TEAMROLE_REQUEST)
							.setTeamId(team.getId())
							.setTimestamps(GregorianCalendar.getInstance()));
					notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_REQUEST, getTeamAdmin(team), profile.getId(), team.getId());
					return true;
				} else if(isOwnerTeam(team, user.getProfile()) && null != currentRole){					
					roleService.createOrUpdate(currentRole
							.setRole(SustainappConstantes.TEAMROLE_MEMBER));
					badgeService.capitaine(user.getProfile());
					notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_ACCEPT, profile.getId(), user.getProfile().getId(), team.getId());
					return true;
				}
				break;
		}
		return false;
	}
	
	/**
	 * Verify if a connected user is owner of a team
	 * @param team
	 * @param request
	 * @return
	 */
	private boolean isConnectedOwner(TeamEntity team, HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);
		if(null!= user && user.getIsAdmin()){
			return true;
		}
		if(null == user || null == user.getProfile() || !isOwnerTeam(team, user.getProfile())){
			return false;
		}
		return true;
	}
	
	/**
	 * Get the owner of a team
	 * @param team
	 * @return
	 */
	private Long getTeamAdmin(TeamEntity team){
		for(TeamRoleEntity role : team.getListRole()){
			if(role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN)){
				return role.getProfilId();
			}
		}
		return null;
	}
	
	/**
	 * Verify if a profile is owner of a team
	 * @param team
	 * @param profile
	 * @return
	 */
	private boolean isOwnerTeam(TeamEntity team, ProfileEntity profile){
		return searchRoleProfile(team, profile).equals(SustainappConstantes.TEAMROLE_ADMIN);
	}
	
	
	/**
	 * retrieve TeamRole of a profile
	 * @param team
	 * @param profile
	 * @return
	 */
	private TeamRoleEntity searchTeamRoleProfile(TeamEntity team, ProfileEntity profile){
		for(TeamRoleEntity role : team.getListRole()){
			if(role.getProfilId().equals(profile.getId())){
				return role;
			}
		}
		return null;
	}
	
	
	/**
	 * retrieve role of a profile
	 * @param team
	 * @param profile
	 * @return
	 */
	private String searchRoleProfile(TeamEntity team, ProfileEntity profile){
		TeamRoleEntity role = searchTeamRoleProfile(team, profile);
		if(null != role){
			return role.getRole();
		}
		return null;
	}
	
	/**
	 * Retrieve user role by HttpServletRequest
	 * @param team
	 * @param request
	 * @return
	 */
	private String searchRole(TeamEntity team, HttpServletRequest request){
		Optional<Long> idUser = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!idUser.isPresent()){
			return null;
		}
		UserAccountEntity user = super.userService.getById(idUser.get());
		if(null == user || null == user.getProfile()){
			return null;
		}
		if(user.getIsAdmin()){
			return SustainappConstantes.TEAMROLE_ADMIN;
		}
		return searchRoleProfile(team, user.getProfile());
	}
	
	/**
	 * Retrieve profiles by teamRole
	 * @param team
	 * @param role
	 * @return
	 */
	private List<ProfileEntity> getProfileByRole(TeamEntity team, String role){
		List<ProfileEntity> result = new SustainappList<ProfileEntity>();
		for(TeamRoleEntity teamRole : team.getListRole()){
			if(teamRole.getRole().equals(role)){
				result.add(profileService.getById(teamRole.getProfilId()));
			}
		}
		return result;
	}
	
	/**
	 * Verifier toutes les informations nécéssaires à l'action sur une team
	 * @param request
	 * @return
	 */
	public TeamEntity verifyAllOwnerInformations(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("team"));
		if(!id.isPresent()){
			return null;
		}
		TeamEntity team = teamService.getById(id.get());
		if(null == team || !isConnectedOwner(team, request)){
			return null;
		}
		return team;
	}
}