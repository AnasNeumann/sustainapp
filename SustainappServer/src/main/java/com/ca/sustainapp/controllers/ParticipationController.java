package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Collections;
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
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.dao.ChallengeVoteServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.pojo.SustainappList;
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
    public ResponseEntity<String> create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
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
		return success(new IdResponse().setId(idParticipation));
	}
	
	/**
	 * delete a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!isConnected(request) || !idParticipation.isPresent()){
			return super.refuse();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		if(null == participation || (!super.isAdmin(request) && !super.isOwnerParticiaption(participation, super.getUser(request).getProfile()))){
			return super.refuse();
		}
		deleteService.cascadeDelete(participation);
		return success();
	}
	
	/**
	 * vote for a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/vote", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> vote(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!isConnected(request) || !idParticipation.isPresent()){
			return super.refuse();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		Long idProfile = super.getUser(request).getProfile().getId();
		if(null == participation){
			return super.refuse();
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
		return success();
	}
	
	/**
	 * get all votes of a participation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/participation/votes", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getVotes(HttpServletRequest request) {
		Optional<Long> idParticipation = StringsUtils.parseLongQuickly(request.getParameter("participation"));
		if(!idParticipation.isPresent()){
			return super.refuse();
		}
		ParticipationEntity participation = participationService.getById(idParticipation.get());
		if(null == participation){
			return super.refuse();
		}
		List<VoteResponse> votes = new SustainappList<VoteResponse>();
		Collections.sort(participation.getVotes(), compartor);
		for(ChallengeVoteEntity vote : participation.getVotes()){
			votes.add(new VoteResponse()
					.setTimestamps(vote.getTimestamps())
					.setProfile(new LightProfileResponse(profileService.getById(vote.getProfilId()))));
		}
		return success(new VotesResponse().setVotes(votes));
	}
	
	/**
	 * Notifier le(s) proriétaire d'une participation qu'elle a reçue un vote
	 * @param creatorId
	 * @param participation
	 */
	private void notifyVote(Long creatorId, ParticipationEntity participation){
		if(participation.getTargetType().equals(SustainappConstantes.TARGET_PROFILE)){
			notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VOTE, participation.getTargetId(), creatorId, participation.getId());
		}else{
			List<TeamRoleEntity> roles = getService.cascadeGetTeamRole(new TeamRoleCriteria().setTeamId(participation.getTargetId()));
			for(TeamRoleEntity role : roles){
				if(role.getRole().equals(SustainappConstantes.TEAMROLE_MEMBER) || role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN)){
					notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VOTE, role.getProfilId(), creatorId, participation.getId());
				}
			}			
		}
	}
	
}