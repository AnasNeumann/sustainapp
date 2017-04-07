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
 * ANSWER_CATEGORY table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
@Entity
@Table(name = "ANSWER_CATEGORY")
@SequenceGenerator(name = "answer_category_id_seq_generator", sequenceName = "answer_category_id_seq")
public class AnswerCategoryEntity extends GenericNumerotableEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "QUESTION_ID")
	private Long questionId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "NUMERO")
	private Integer numero;

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
	public AnswerCategoryEntity setId(Long id) {
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
	public AnswerCategoryEntity setQuestionId(Long questionId) {
		this.questionId = questionId;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public AnswerCategoryEntity setName(String name) {
		this.name = name;
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
	public AnswerCategoryEntity setNumero(Integer numero) {
		this.numero = numero;
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
	public AnswerCategoryEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}