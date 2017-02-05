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
 * ANSWER table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "ANSWER")
@SequenceGenerator(name = "answer_id_seq_generator", sequenceName = "answer_id_seq")
public class AnswerEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "QUESTION_ID")
	private Long questionId;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "IS_TRUE")
	private Boolean isTrue;

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
	public AnswerEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public AnswerEntity setQuestionId(Long questionId) {
		this.questionId = questionId;
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
	public AnswerEntity setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * @return the isTrue
	 */
	public Boolean getIsTrue() {
		return isTrue;
	}

	/**
	 * @param isTrue the isTrue to set
	 */
	public AnswerEntity setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
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
	public AnswerEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}