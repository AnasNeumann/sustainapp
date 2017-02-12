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
public class ChallengeVoteCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long profilId;
	private Long participationId;
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
	public ChallengeVoteCriteria setId(Long id) {
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
	 * @param profilId
	 *            the profilId to set
	 */
	@QueryParam("profilId")
	public ChallengeVoteCriteria setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the topicId
	 */
	public Long getParticipationId() {
		return participationId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	@QueryParam("participationId")
	public ChallengeVoteCriteria setParticipationId(Long participationId) {
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
	 * @param timestamps
	 *            the timestamps to set
	 */
	@QueryParam("timestamps")
	public ChallengeVoteCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}