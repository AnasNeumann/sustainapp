package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * RESEARCH table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
@Entity
@Table(name = "RESEARCH")
@SequenceGenerator(name = "research_id_seq_generator", sequenceName = "research_id_seq")
@Cacheable(false)
public class ResearchEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "research_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "QUERY")
	private String query;
	
	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@Column(name = "TIMESTAMPS")
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
	public ResearchEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public ResearchEntity setQuery(String query) {
		this.query = query;
		return this;
	}

	/**
	 * @return the profilId
	 */
	public Long getProfilId() {
		return profilId;
	}

	/**
	 * @param profilId the profilId to set
	 */
	public ResearchEntity setProfilId(Long profilId) {
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
	 * @param timestamps the timestamps to set
	 */
	public ResearchEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}