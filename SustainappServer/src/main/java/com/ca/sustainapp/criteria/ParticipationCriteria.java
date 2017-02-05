package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class ParticipationCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer score;
	private Long profilId;
	private Long challengeId;
	private Calendar timestamps;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@QueryParam("id")
	public ParticipationCriteria setId(Long id) {
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
	 * @param score
	 *            the score to set
	 */
	@QueryParam("score")
	public ParticipationCriteria setScore(Integer score) {
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
	 * @param profilId
	 *            the profilId to set
	 */
	@QueryParam("profilId")
	public ParticipationCriteria setProfilId(Long profilId) {
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
	 * @param challengeId
	 *            the challengeId to set
	 */
	@QueryParam("challengeId")
	public ParticipationCriteria setChallengeId(Long challengeId) {
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
	 * @param timestamps
	 *            the timestamps to set
	 */
	@QueryParam("timestamps")
	public ParticipationCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}