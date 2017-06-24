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
 * TOPIC_VALIDATION table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "TOPIC_VALIDATION")
@SequenceGenerator(name = "topic_validation_id_seq_generator", sequenceName = "topic_validation_id_seq")
@Cacheable(false)
public class TopicValidationEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_validation_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@Column(name = "TOPIC_ID")
	private Long topicId;
	
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
	public TopicValidationEntity setId(Long id) {
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
	public TopicValidationEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the topicId
	 */
	public Long getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public TopicValidationEntity setTopicId(Long topicId) {
		this.topicId = topicId;
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
	public TopicValidationEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}