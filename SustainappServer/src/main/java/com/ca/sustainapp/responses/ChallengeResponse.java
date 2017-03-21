package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage d'un challenge
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 21/03/2017
 * @version 1.0
 */
public class ChallengeResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private ProfileEntity owner;
	private ChallengeEntity challenge;
	private List<ParticipationResponse> participations = new SustainappList<ParticipationResponse>();
	private List<TeamEntity> teams = new SustainappList<TeamEntity>();

	/**
	 * @return the owner
	 */
	public ProfileEntity getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public ChallengeResponse setOwner(ProfileEntity owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * @return the challenge
	 */
	public ChallengeEntity getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge
	 *            the challenge to set
	 */
	public ChallengeResponse setChallenge(ChallengeEntity challenge) {
		this.challenge = challenge;
		return this;
	}

	/**
	 * @return the participations
	 */
	public List<ParticipationResponse> getParticipations() {
		return participations;
	}

	/**
	 * @param participations
	 *            the participations to set
	 */
	public ChallengeResponse setParticipations(List<ParticipationResponse> participations) {
		this.participations = participations;
		return this;
	}

	/**
	 * @return the teams
	 */
	public List<TeamEntity> getTeams() {
		return teams;
	}

	/**
	 * @param teams
	 *            the teams to set
	 */
	public ChallengeResponse setTeams(List<TeamEntity> teams) {
		this.teams = teams;
		return this;
	}
}