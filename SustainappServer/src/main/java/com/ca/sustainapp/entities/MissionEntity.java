package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * MISSION table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "MISSION")
@SequenceGenerator(name = "mission_id_seq_generator", sequenceName = "mission_id_seq")
public class MissionEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mission_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "STATE")
	private Integer state;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "CREATOR_ID")
	private Long creatorId;
	
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
	public MissionEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public MissionEntity setState(Integer state) {
		this.state = state;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public MissionEntity setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public MissionEntity setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
	public MissionEntity setProfilId(Long profilId) {
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
	public MissionEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}