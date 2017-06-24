package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * CHALLENGE_VOTE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "CHALLENGE_VOTE")
@SequenceGenerator(name = "challenge_vote_id_seq_generator", sequenceName = "challenge_vote_id_seq")
@Cacheable(false)
public class ChallengeVoteEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_vote_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@Column(name = "PARTICIPATION_ID")
	private Long participationId;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public ChallengeVoteEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the profilId
	 */
	public Long getProfilId() {
		return profilId;
	}

	/**
	 * @param profilId the profilId to set
	 */
	public ChallengeVoteEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the participationId
	 */
	public Long getParticipationId() {
		return participationId;
	}

	/**
	 * @param participationId the participationId to set
	 */
	public ChallengeVoteEntity setParticipationId(Long participationId) {
		this.participationId = participationId;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps the timestamps to set
	 */
	public ChallengeVoteEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}


}