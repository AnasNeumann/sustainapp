package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class ProfileCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String lastName;
	private String firstName;
	private Calendar bornDate;
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
	public ProfileCriteria setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	@QueryParam("lastName")
	public ProfileCriteria setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	@QueryParam("firstName")
	public ProfileCriteria setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the bornDate
	 */
	public Calendar getBornDate() {
		return bornDate;
	}

	/**
	 * @param bornDate the bornDate to set
	 */
	@QueryParam("bornDate")
	public ProfileCriteria setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
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
	public ProfileCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}