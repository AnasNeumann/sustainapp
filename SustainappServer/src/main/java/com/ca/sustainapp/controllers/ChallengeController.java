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
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.ChallengeResponse;
import com.ca.sustainapp.responses.ChallengeTypesResponse;
import com.ca.sustainapp.responses.ChallengesResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.ParticipationResponse;
import com.ca.sustainapp.services.CascadeDeleteService;
import com.ca.sustainapp.services.CascadeGetService;
import com.ca.sustainapp.utils.DateUtils;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ChallengeUpdateValidator;
import com.ca.sustainapp.validators.ChallengeValidator;

/**
 * Restfull controller for challenge management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ChallengeController extends GenericController {

	/**
	 * DAO services
	 */
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private ChallengeTypeServiceDAO challengeTypeService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private TeamServiceDAO teamService;
	
	/**
	 * Business services
	 */
	@Autowired
	private CascadeGetService getService;
	@Autowired
	private CascadeDeleteService deleteService;
	
	/**
	 * Validators
	 */
	@Autowired
	private ChallengeValidator validator;
	@Autowired
	private ChallengeUpdateValidator updateValidator;
	
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
	 * create a new challenge
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		ChallengeEntity challenge = new ChallengeEntity()
				.setName(request.getParameter("name"))
				.setAbout(request.getParameter("about"))
				.setEndDate(DateUtils.ionicParse(request.getParameter("endDate"),GregorianCalendar.getInstance()))
				.setChallengeType(challengeTypeService.getById(StringsUtils.parseLongQuickly(request.getParameter("type")).get()))
				.setTeamEnabled(new Boolean(request.getParameter("teamEnabled")))
				.setMinLevel(StringsUtils.parseIntegerQuietly(request.getParameter("levelMin")).get())
				.setTimestamps(GregorianCalendar.getInstance())
				.setCreatorId(super.getConnectedUser(request).getProfile().getId());
		if(!isEmpty(request.getParameter("file"))){
			challenge.setIcon(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		}
		Long idChallenge = challengeService.createOrUpdate(challenge);
		return new IdResponse().setId(idChallenge).setCode(1).buildJson();
	}
	
	/**
	 * get a challenge by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		Optional<Long> challengeId = StringsUtils.parseLongQuickly(request.getParameter("challenge"));
		Optional<Long> userId = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!challengeId.isPresent() || !userId.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ChallengeResponse response = new ChallengeResponse().setChallenge(challengeService.getById(challengeId.get()));
		if(null == response.getChallenge()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ProfileEntity profile = super.getProfileByUser(userId.get());
		return response
				.setOwner(profileService.getById(response.getChallenge().getCreatorId()))
				.setTeams(searchTeam(profile.getId()))
				.setParticipations(searchAllParticipations(profile.getId(),response.getChallenge()))
				.setIsAdmin(response.getOwner().getId().equals(profile.getId()))
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * update challenge icon
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/icon", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String icon(HttpServletRequest request) {		
		ChallengeEntity challenge = verifyAllOwnerInformations(request);
		if(null == challenge){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		challengeService.createOrUpdate(challenge.setIcon(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG)));
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * update challenge informations
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {	
		String name = request.getParameter("name");
		String about = request.getParameter("about");
		ChallengeEntity challenge = verifyAllOwnerInformations(request);
		if(null == challenge || !updateValidator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		challengeService.createOrUpdate(challenge.setName(name).setAbout(about));
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * delete a challenge
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/challenge/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		ChallengeEntity challenge = verifyAllOwnerInformations(request);
		if(null == challenge){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		deleteService.cascadeDelete(challenge);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}

	/**
	 * create a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/create", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String participate(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String deleteParticipation(HttpServletRequest request) {
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

	/**
	 * Rechercher toutes les teams pour lesquels un user est membre ou owner
	 * @param profileId
	 * @return
	 */ 
	private List<TeamEntity> searchTeam(Long profileId){
		List<TeamRoleEntity> roles = getService.cascadeGetTeamRole(new TeamRoleCriteria().setProfilId(profileId));
		List<TeamEntity> teams = new SustainappList<TeamEntity>();
		for(TeamRoleEntity role : roles){
			if(role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN) || role.getRole().equals(SustainappConstantes.TEAMROLE_MEMBER)){
				teams.add(teamService.getById(role.getTeamId()));
			}
		}
		return teams;
	}
	
	/**
	 * Rechercher toutes les participations a un challenge
	 * @param profileId
	 * @param challengeId
	 * @return
	 */
	private List<ParticipationResponse> searchAllParticipations(Long profileId, ChallengeEntity challenge){
		List<ParticipationEntity> participations = getService.cascadeGetParticipations(new ParticipationCriteria().setChallengeId(challenge.getId()));
		List<ParticipationResponse> result = new SustainappList<ParticipationResponse>();
		ChallengeVoteEntity currentVote = searchVote(participations, profileId);
		for(ParticipationEntity participation : participations){
			ParticipationResponse response = new ParticipationResponse()
					.setParticipation(participation)
					.setAlreadyVoted(alreadyVoted(currentVote, participation.getId()))
					.setIsOwner(isOwnerParticiaption(participation, profileId));
			if(participation.getTargetType().equals(SustainappConstantes.TARGET_TEAM)){
				response.setOwnerTeam(teamService.getById(participation.getTargetId()));
			} else {
				response.setOwnerProfil(profileService.getById(participation.getTargetId()));
			}
			result.add(response);
		}
		return result;
	}
	
	/**
	 * Verify if a profile is owner of a participation
	 * @param participation
	 * @param idProfil
	 * @return
	 */
	private boolean isOwnerParticiaption(ParticipationEntity participation, Long idProfil){
		if(participation.getTargetType().equals(SustainappConstantes.TARGET_PROFILE) && participation.getTargetId().equals(idProfil)){
			return true;
		}else{
			for(TeamEntity team : searchTeam(idProfil)){
				if(team.getId().equals(participation.getTargetId())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * verifier si un profil a voté pour une participation ou non
	 * @param vote
	 * @param idParticipation
	 * @return
	 */
	private boolean alreadyVoted(ChallengeVoteEntity vote, Long idParticipation){
		if(null != vote && vote.getParticipationId().equals(idParticipation)){
			return true;
		}
		return false;
	}

	/**
	 * Recuperer le vote d'un profil sur les participations d'un challenge
	 * @param participations
	 * @param id
	 * @return
	 */
	private ChallengeVoteEntity searchVote(List<ParticipationEntity> participations, Long id){
		for(ParticipationEntity participation : participations){
			for(ChallengeVoteEntity vote : participation.getVotes()){
				if(vote.getProfilId().equals(id)){
					return vote;
				}
			}
		}
		return null;
	}
	
	/**
	 * Verifier toutes les informations avant modification d'un challenge
	 * @param request
	 * @return
	 */
	private ChallengeEntity verifyAllOwnerInformations(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("challenge"));
		if(!id.isPresent()){
			return null;
		}
		ChallengeEntity challenge = challengeService.getById(id.get());
		if(null == challenge || !challenge.getCreatorId().equals(super.getConnectedUser(request).getProfile().getId())){
			return null;
		}
		return challenge;
	}
}