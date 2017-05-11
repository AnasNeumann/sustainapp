package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
public class PlacePictureCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long placeId;
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
	@QueryParam("id")
	public PlacePictureCriteria setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the placeId
	 */
	public Long getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId the placeId to set
	 */
	@QueryParam("placeId")
	public PlacePictureCriteria setPlaceId(Long placeId) {
		this.placeId = placeId;
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
	@QueryParam("timestamps")
	public PlacePictureCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}
