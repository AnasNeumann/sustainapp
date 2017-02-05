package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TRAVEL table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "TRAVEL")
@SequenceGenerator(name = "travel_id_seq_generator", sequenceName = "travel_id_seq")
public class TravelEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@ManyToOne
	@JoinColumn(name = "TRANSPORT_ID", referencedColumnName = "ID")
	private TransportEntity transport;
	
	@Column(name = "TOTAL_TIME")
	private Integer totalTime;
	
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
	public TravelEntity setId(Long id) {
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
	 * @param profilId the profilId to set
	 */
	public TravelEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the transport
	 */
	public TransportEntity getTransport() {
		return transport;
	}

	/**
	 * @param transport the transport to set
	 */
	public TravelEntity setTransport(TransportEntity transport) {
		this.transport = transport;
		return this;
	}

	/**
	 * @return the totalTime
	 */
	public Integer getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public TravelEntity setTotalTime(Integer totalTime) {
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
	 * @param timestamps the timestamps to set
	 */
	public TravelEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}