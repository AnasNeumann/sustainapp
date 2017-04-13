package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage d'un challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 21/03/2017
 * @version 1.0
 */
public class ChallengeResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private ProfileEntity owner;
	private Boolean isAdmin;
	private Boolean isOpen;
	private Long currentVote;
	private ChallengeEntity challenge;
	private List<ParticipationResponse> participations = new SustainappList<ParticipationResponse>();
	private List<LightProfileResponse> lightProfiles = new SustainappList<LightProfileResponse>();

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
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public ChallengeResponse setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	/**
	 * @return the isOpen
	 */
	public Boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public ChallengeResponse setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
		return this;
	}

	/**
	 * @return the currentVote
	 */
	public Long getCurrentVote() {
		return currentVote;
	}

	/**
	 * @param currentVote the currentVote to set
	 */
	public ChallengeResponse setCurrentVote(Long currentVote) {
		this.currentVote = currentVote;
		return this;
	}

	/**
	 * @return the lightProfiles
	 */
	public List<LightProfileResponse> getLightProfiles() {
		return lightProfiles;
	}

	/**
	 * @param lightProfiles the lightProfiles to set
	 */
	public ChallengeResponse setLightProfiles(List<LightProfileResponse> lightProfiles) {
		this.lightProfiles = lightProfiles;
		return this;
	}
}