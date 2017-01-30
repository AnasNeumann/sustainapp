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
public class TransportCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer energicalValue;
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
	public TransportCriteria setId(Long id) {
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
	public TransportCriteria setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the energicalValue
	 */
	public Integer getEnergicalValue() {
		return energicalValue;
	}

	/**
	 * @param energicalValue
	 *            the energicalValue to set
	 */
	@QueryParam("energicalValue")
	public TransportCriteria setEnergicalValue(Integer energicalValue) {
		this.energicalValue = energicalValue;
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
	public TransportCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}