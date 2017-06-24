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
 * QUESTION table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "QUESTION")
@SequenceGenerator(name = "question_id_seq_generator", sequenceName = "question_id_seq")
@Cacheable(false)
public class QuestionEntity extends GenericNumerotableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TOPIC_ID")
	private Long topicId;
	
	@Column(name = "QUESTION_TYPE")
	private Integer type;
	
	@Column(name = "NUMERO")
	private Integer numero;

	@Column(name = "PICTURE")
	private byte[] picture;
	
	@Column(name = "MESSAGE")
	private String message;

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
	public QuestionEntity setId(Long id) {
		this.id = id;
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
	public QuestionEntity setTopicId(Long topicId) {
		this.topicId = topicId;
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
	public QuestionEntity setMessage(String message) {
		this.message = message;
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
	public QuestionEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public QuestionEntity setType(Integer type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public QuestionEntity setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

	/**
	 * @return the picture
	 */
	public byte[] getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public QuestionEntity setPicture(byte[] picture) {
		this.picture = picture;
		return this;
	}
}