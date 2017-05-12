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
public class CityCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer actif;
	private Long userId;
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
	public CityCriteria setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
	 * @return the actif
	 */
	public Integer getActif() {
		return actif;
	}
	
	/**
	 * @param actif the actif to set
	 */
	@QueryParam("actif")
	public CityCriteria setActif(Integer actif) {
		this.actif = actif;
		return this;
	}
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	@QueryParam("userId")
	public CityCriteria setUserId(Long userId) {
		this.userId = userId;
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
	public CityCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}