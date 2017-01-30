package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * PARTICIPATION table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "PARTICIPATION")
@SequenceGenerator(name = "participation_id_seq_generator", sequenceName = "participation_id_seq")
public class ParticipationEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participation_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SCORE")
	private Integer score;

	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@Column(name = "CHALLENGE_ID")
	private Long challengeId;
	
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
	public ParticipationEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public ParticipationEntity setScore(Integer score) {
		this.score = score;
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
	public ParticipationEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the challengeId
	 */
	public Long getChallengeId() {
		return challengeId;
	}

	/**
	 * @param challengeId the challengeId to set
	 */
	public ParticipationEntity setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
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
	public ParticipationEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}