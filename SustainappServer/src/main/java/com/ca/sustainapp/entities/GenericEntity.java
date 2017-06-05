package com.ca.sustainapp.entities;

import java.util.Calendar;

/**
 * Mother class for all entities
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 30/01/2017
 * 
 */
public class GenericEntity {

	protected Calendar timestamps;
	protected Long id;
	
	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}
	
	/**
	 * @param timestamps the timestamps to set
	 */
	public GenericEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public GenericEntity setId(Long id) {
		this.id = id;
		return this;
	}
}