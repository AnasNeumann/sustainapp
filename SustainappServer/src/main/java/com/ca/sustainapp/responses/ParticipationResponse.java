package com.ca.sustainapp.responses;

import java.io.Serializable;

import com.ca.sustainapp.entities.ParticipationEntity;

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
	private Integer nbrVotes;
	private LightProfileResponse owner;

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
		this.nbrVotes = participation.getVotes().size();
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
	 * @return the owner
	 */
	public LightProfileResponse getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public ParticipationResponse setOwner(LightProfileResponse owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * @return the nbrVotes
	 */
	public Integer getNbrVotes() {
		return nbrVotes;
	}

	/**
	 * @param nbrVotes the nbrVotes to set
	 */
	public ParticipationResponse setNbrVotes(Integer nbrVotes) {
		this.nbrVotes = nbrVotes;
		return this;
	}
}