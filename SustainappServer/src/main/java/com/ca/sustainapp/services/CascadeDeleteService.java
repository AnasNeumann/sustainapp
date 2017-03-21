package com.ca.sustainapp.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.repositories.ChallengeRepository;
import com.ca.sustainapp.repositories.ChallengeVoteRepository;
import com.ca.sustainapp.repositories.ParticipationRepository;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.repositories.TeamRoleRepository;

/**
 * Service pour la suppression en cascade
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2107
 * @verion 1.0
 */
@Service("deleteService")
public class CascadeDeleteService {

	/**
	 * Les repositories
	 */
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	TeamRoleRepository roleRepository;
	@Autowired
	ChallengeRepository challengeRepository;
	@Autowired
	ChallengeVoteRepository voteRepository;
	@Autowired
	ParticipationRepository participationRepository;

	/**
	 * les services
	 */
	@Autowired
	cascadeGetService getService;
	
	/**
	 * cascade delete a challenge
	 * @param challenge
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ChallengeEntity challenge){
		for(ParticipationEntity participation : getService.cascadeGetParticipations(new ParticipationCriteria().setChallengeId(challenge.getId()))){
			cascadeDelete(participation);
		}
		challengeRepository.delete(challenge.getId());
	}
	
	/**
	 * cascade delete a team
	 * @param team
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamEntity team){
		for(ParticipationEntity participation : getService.cascadeGetParticipations(new ParticipationCriteria().setTargetId(team.getId()).setTargetType(SustainappConstantes.TARGET_TEAM))){
			cascadeDelete(participation);
		}
		for(TeamRoleEntity role : team.getListRole()){
			cascadeDelete(role);
		}
		teamRepository.delete(team.getId());
	}
	
	/**
	 * cascade delete a participation
	 * @param participation
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ParticipationEntity participation){
		for(ChallengeVoteEntity vote : participation.getVotes()){
			cascadeDelete(vote);
		}
		participationRepository.delete(participation.getId());
	}
	
	/**
	 * cascade delete a vote
	 * @param vote
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ChallengeVoteEntity vote){
		voteRepository.delete(vote.getId());
	}

	/**
	 * cascade delete a teamRole
	 * @param team
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamRoleEntity role){
		roleRepository.delete(role.getId());
	}

}