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
public class NotificationCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String message;
	private Integer creatorType;
	private Long linkId;
	private Integer state;
	private Long creatorId;
	private Long profilId;
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
	public NotificationCriteria setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	@QueryParam("message")
	public NotificationCriteria setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * @return the creatorType
	 */
	public Integer getCreatorType() {
		return creatorType;
	}

	/**
	 * @param creatorType
	 *            the creatorType to set
	 */
	@QueryParam("creatorType")
	public NotificationCriteria setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
		return this;
	}

	/**
	 * @return the linkId
	 */
	public Long getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId
	 *            the linkId to set
	 */
	@QueryParam("linkId")
	public NotificationCriteria setLinkId(Long linkId) {
		this.linkId = linkId;
		return this;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	@QueryParam("state")
	public NotificationCriteria setState(Integer state) {
		this.state = state;
		return this;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId
	 *            the creatorId to set
	 */
	@QueryParam("creatorId")
	public NotificationCriteria setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
	public NotificationCriteria setProfilId(Long profilId) {
		this.profilId = profilId;
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
	public NotificationCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}