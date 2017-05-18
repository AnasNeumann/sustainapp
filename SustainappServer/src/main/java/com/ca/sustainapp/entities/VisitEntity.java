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
 * VISIT table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/05/2017
 * @version 1.0
 */
@Entity
@Table(name = "VISIT")
@SequenceGenerator(name = "visit_id_seq_generator", sequenceName = "visit_id_seq")
public class VisitEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_note_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PLACE_ID")
	private Long placeId;

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
	public VisitEntity setId(Long id) {
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
	public VisitEntity setPlaceId(Long placeId) {
		this.placeId = placeId;
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
	public VisitEntity setProfilId(Long profilId) {
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
	public VisitEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}