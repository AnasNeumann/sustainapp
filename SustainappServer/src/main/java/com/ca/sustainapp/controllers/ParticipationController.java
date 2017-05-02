package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Collections;
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
import com.ca.sustainapp.dao.ChallengeVoteServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.LightProfileResponse;
import com.ca.sustainapp.responses.VoteResponse;
import com.ca.sustainapp.responses.VotesResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ParticipationValidator;

/**
 * Restfull controller for participation management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ParticipationController extends GenericChallengeController {
	
	/**
	 * Service
	 */
	@Autowired
	private ParticipationServiceDAO participationService;
	@Autowired
	private ChallengeVoteServiceDAO voteService;
	
	/**
	 * Validators
	 */
	@Autowired
	private ParticipationValidator validator;
	
	/**
	 * create a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/create", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		Long idChallenge = StringsUtils.parseLongQuickly(request.getParameter("challenge")).get();
		Long creatorId = StringsUtils.parseLongQuickly(request.getParameter("targetId")).get();
		ParticipationEntity participation = new ParticipationEntity()
				.setAbout(request.getParameter("about"))
				.setTitle(request.getParameter("title"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setTargetType(request.getParameter("targetType"))
				.setChallengeId(idChallenge)
				.setTargetId(creatorId);
		if(!isEmpty(request.getParameter("file"))){
			participation.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		}
		Long idParticipation = participationService.createOrUpdate(participation);
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_PARTICIPATE, challengeService.getById(idChallenge).getCreatorId(), creatorId, idChallenge, request.getParameter("targetType").equals(SustainappConstantes.TARGET_PROFILE)? 0 : 1);
		return new IdResponse().setId(idParticipation).setCode(1).buildJson();
	}
	
	/**
	 * delete a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!isConnected(request) || !idParticipation.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		if(null == participation || (!super.isAdmin(request) && !super.isOwnerParticiaption(participation, super.getConnectedUser(request).getProfile()))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		deleteService.cascadeDelete(participation);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * vote for a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/vote", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String vote(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!isConnected(request) || !idParticipation.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		Long idProfile = super.getConnectedUser(request).getProfile().getId();
		if(null == participation){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ChallengeEntity challenge = challengeService.getById(participation.getChallengeId());
		List<ParticipationEntity> participations = getService.cascadeGetParticipations(new ParticipationCriteria().setChallengeId(challenge.getId()));
		ChallengeVoteEntity currentVote = searchVote(participations, idProfile);
		badgeService.handleVoteParticipation(idParticipation.get());
		if(null == currentVote){
			currentVote = new ChallengeVoteEntity()
					.setParticipationId(idParticipation.get())
					.setProfilId(idProfile)
					.setTimestamps(GregorianCalendar.getInstance());
			voteService.createOrUpdate(currentVote);
			notifyVote(idProfile, participation);
		} else {
			if(currentVote.getParticipationId().equals(idParticipation.get())){
				deleteService.cascadeDelete(currentVote);
			} else {
				currentVote.setParticipationId(idParticipation.get()).setTimestamps(GregorianCalendar.getInstance());
				voteService.createOrUpdate(currentVote);
				notifyVote(idProfile, participation);
			}
		}
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * get all votes of a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/votes", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getVotes(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!idParticipation.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		if(null == participation){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<VoteResponse> votes = new SustainappList<VoteResponse>();
		Collections.sort(participation.getVotes(), compartor);
		for(ChallengeVoteEntity vote : participation.getVotes()){
			votes.add(new VoteResponse()
					.setTimestamps(vote.getTimestamps())
					.setProfile(new LightProfileResponse(profileService.getById(vote.getProfilId()))));
		}
		return new VotesResponse().setVotes(votes).setCode(1).buildJson();
	}
	
	/**
	 * Notifier le(s) proriétaire d'une participation qu'elle a reçue un vote
	 * @param creatorId
	 * @param participation
	 */
	private void notifyVote(Long creatorId, ParticipationEntity participation){
		if(participation.getTargetType().equals(SustainappConstantes.TARGET_PROFILE)){
			notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VOTE, participation.getTargetId(), creatorId, participation.getChallengeId());
		}else{
			List<TeamRoleEntity> roles = getService.cascadeGetTeamRole(new TeamRoleCriteria().setTeamId(participation.getTargetId()));
			for(TeamRoleEntity role : roles){
				if(role.getRole().equals(SustainappConstantes.TEAMROLE_MEMBER) || role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN)){
					notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VOTE, role.getProfilId(), creatorId, participation.getChallengeId());
				}
			}			
		}
	}
	
}