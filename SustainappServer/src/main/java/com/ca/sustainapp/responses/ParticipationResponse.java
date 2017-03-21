package com.ca.sustainapp.responses;

import java.io.Serializable;

import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;

/**
 * Json de présentation d'une participation
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 21/03/2017
 * @version 1.0
 */
public class ParticipationResponse implements Serializable {
	protected static final long serialVersionUID = 1L;
	private ParticipationEntity participation;
	private Boolean isOwner;
	private Boolean alreadyVoted;
	private ProfileEntity ownerProfil;
	private TeamEntity ownerTeam;

	/**
	 * @return the participation
	 */
	public ParticipationEntity getParticipation() {
		return participation;
	}

	/**
	 * @param participation
	 *            the participation to set
	 */
	public ParticipationResponse setParticipation(ParticipationEntity participation) {
		this.participation = participation;
		return this;
	}

	/**
	 * @return the isOwner
	 */
	public Boolean getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner
	 *            the isOwner to set
	 */
	public ParticipationResponse setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
		return this;
	}

	/**
	 * @return the alreadyVoted
	 */
	public Boolean getAlreadyVoted() {
		return alreadyVoted;
	}

	/**
	 * @param alreadyVoted
	 *            the alreadyVoted to set
	 */
	public ParticipationResponse setAlreadyVoted(Boolean alreadyVoted) {
		this.alreadyVoted = alreadyVoted;
		return this;
	}

	/**
	 * @return the ownerProfil
	 */
	public ProfileEntity getOwnerProfil() {
		return ownerProfil;
	}

	/**
	 * @param ownerProfil
	 *            the ownerProfil to set
	 */
	public ParticipationResponse setOwnerProfil(ProfileEntity ownerProfil) {
		this.ownerProfil = ownerProfil;
		return this;
	}

	/**
	 * @return the ownerTeam
	 */
	public TeamEntity getOwnerTeam() {
		return ownerTeam;
	}

	/**
	 * @param ownerTeam
	 *            the ownerTeam to set
	 */
	public ParticipationResponse setOwnerTeam(TeamEntity ownerTeam) {
		this.ownerTeam = ownerTeam;
		return this;
	}

}