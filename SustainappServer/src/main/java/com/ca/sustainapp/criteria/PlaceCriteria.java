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
public class PlaceCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Float longitude;
	private Float latitude;
	private Long cityId;
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
	public PlaceCriteria setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}
	
	/**
	 * @param longitude the longitude to set
	 */
	@QueryParam("longitude")
	public PlaceCriteria setLongitude(Float longitude) {
		this.longitude = longitude;
		return this;
	}
	
	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}
	
	/**
	 * @param latitude the latitude to set
	 */
	@QueryParam("latitude")
	public PlaceCriteria setLatitude(Float latitude) {
		this.latitude = latitude;
		return this;
	}
	
	/**
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}
	
	/**
	 * @param cityId the cityId to set
	 */
	@QueryParam("cityId")
	public PlaceCriteria setCityId(Long cityId) {
		this.cityId = cityId;
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
	public PlaceCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}
