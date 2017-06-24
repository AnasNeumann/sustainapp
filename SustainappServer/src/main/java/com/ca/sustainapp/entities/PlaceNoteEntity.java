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
 * PLACE_NOTE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Entity
@Table(name = "PLACE_NOTE")
@SequenceGenerator(name = "place_note_id_seq_generator", sequenceName = "place_note_id_seq")
@Cacheable(false)
public class PlaceNoteEntity extends GenericEntity implements Serializable {
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
	
	@Column(name = "SCORE")
	private Integer score;
	
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
	public PlaceNoteEntity setId(Long id) {
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
	public PlaceNoteEntity setPlaceId(Long placeId) {
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
	public PlaceNoteEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public PlaceNoteEntity setScore(Integer score) {
		this.score = score;
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
	public PlaceNoteEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}