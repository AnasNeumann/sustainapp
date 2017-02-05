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
public class TravelCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long profilId;
	private Integer totalTime;
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
	public TravelCriteria setId(Long id) {
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
	public TravelCriteria setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the totalTime
	 */
	public Integer getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	@QueryParam("totalTime")
	public TravelCriteria setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
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
	public TravelCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}