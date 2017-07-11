package com.ca.sustainapp.controllers;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.LightProfileResponse;
import com.ca.sustainapp.responses.ParticipationResponse;
import com.ca.sustainapp.utils.ListUtils;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Restfull controller for challenge management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class GenericChallengeController extends GenericController {

	/**
	 * DAO services
	 */
	@Autowired
	protected ChallengeServiceDAO challengeService;
	@Autowired
	protected ChallengeTypeServiceDAO challengeTypeService;
	@Autowired
	protected ProfileServiceDAO profileService;
	@Autowired
	protected TeamServiceDAO teamService;
	
	/**
	 * Rechercher toutes les teams et le profil pour lesquels un user est membre ou owner
	 * @param profile
	 * @return
	 */
	protected List<LightProfileResponse> searchAllProfiles(ProfileEntity profile){
		List<TeamRoleEntity> roles = getService.cascadeGet(new TeamRoleCriteria().setProfilId(profile.getId()));
		List<LightProfileResponse> allProfiles = new SustainappList<LightProfileResponse>().put(new LightProfileResponse(profile));
		for(TeamRoleEntity role : roles){
			if(role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN) || role.getRole().equals(SustainappConstantes.TEAMROLE_MEMBER)){
				allProfiles.add(new LightProfileResponse(teamService.getById(role.getTeamId())));
			}
		}
		return allProfiles;
	}

	/**
	 * Rechercher toutes les participations a un challenge
	 * @param participations
	 * @param currentVote
	 * @param profile
	 * @return
	 */
	protected List<ParticipationResponse> searchAllParticipations(List<ParticipationEntity> participations, ChallengeVoteEntity currentVote, ProfileEntity profile, boolean isAdmin){		
		List<ParticipationResponse> result = new SustainappList<ParticipationResponse>();
		for(ParticipationEntity participation : participations){
			ParticipationResponse response = new ParticipationResponse()
					.setParticipation(participation)
					.setIsOwner(isAdmin || isOwnerParticiaption(participation, profile));
			if(participation.getTargetType().equals(SustainappConstantes.TARGET_TEAM)){
				response.setOwner(new LightProfileResponse(teamService.getById(participation.getTargetId())));
			} else {
				response.setOwner(new LightProfileResponse(profileService.getById(participation.getTargetId())));
			}
			result.add(response);
		}	
		return ListUtils.reverseList(result);
	}

	/**
	 * Verify if a profile is owner of a participation
	 * @param participation
	 * @param profile
	 * @return
	 */
	protected boolean isOwnerParticiaption(ParticipationEntity participation, ProfileEntity profile){
		for(LightProfileResponse teamOrProfil : searchAllProfiles(profile)){
			if(participation.getTargetType().equals(teamOrProfil.getType()) && participation.getTargetId().equals(teamOrProfil.getId())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Recuperer le vote d'un profil sur les participations d'un challenge
	 * @param participations
	 * @param id
	 * @return
	 */
	protected ChallengeVoteEntity searchVote(List<ParticipationEntity> participations, Long id){
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
	protected ChallengeEntity verifyAllOwnerInformations(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("challenge"));
		if(!id.isPresent()){
			return null;
		}
		ChallengeEntity challenge = challengeService.getById(id.get());
		if(null !=challenge && super.getConnectedUser(request).getIsAdmin()){
			return challenge;
		}
		if(null == challenge || !challenge.getCreatorId().equals(super.getUser(request).getProfile().getId())){
			return null;
		}
		return challenge;
	}
	
	/**
	 * verify if a challenge is still open or not
	 * @param challenge
	 * @return
	 */
	protected boolean calculateIsOpen(ChallengeEntity challenge){
		 if(GregorianCalendar.getInstance().compareTo(challenge.getEndDate()) >= 0){
			 return false;
		 }
		 return true;
	}
	
}
