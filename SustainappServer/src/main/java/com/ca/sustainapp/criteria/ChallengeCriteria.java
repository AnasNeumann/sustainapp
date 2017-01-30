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
public class ChallengeCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String about;
	private Calendar endDate;
	private Boolean teamEnabled;
	private Long creatorId;
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
	public ChallengeCriteria setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@QueryParam("name")
	public ChallengeCriteria setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * @param about
	 *            the about to set
	 */
	@QueryParam("about")
	public ChallengeCriteria setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	@QueryParam("endDate")
	public ChallengeCriteria setEndDate(Calendar endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * @return the teamEnabled
	 */
	public Boolean getTeamEnabled() {
		return teamEnabled;
	}

	/**
	 * @param teamEnabled
	 *            the teamEnabled to set
	 */
	@QueryParam("teamEnabled")
	public ChallengeCriteria setTeamEnabled(Boolean teamEnabled) {
		this.teamEnabled = teamEnabled;
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
	public ChallengeCriteria setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
	public ChallengeCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}