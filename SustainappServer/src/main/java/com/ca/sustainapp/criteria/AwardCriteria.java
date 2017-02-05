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
public class AwardCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String about;
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
	public AwardCriteria setId(Long id) {
		this.id = id;
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
	public AwardCriteria setAbout(String about) {
		this.about = about;
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
	@QueryParam("timestamps")
	public AwardCriteria setProfilId(Long profilId) {
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
	public AwardCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}