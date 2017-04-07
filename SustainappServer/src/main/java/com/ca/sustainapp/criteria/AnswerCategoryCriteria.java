package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
public class AnswerCategoryCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long questionId;
	private String name;
	private Integer numero;
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
	@QueryParam("id")
	public AnswerCategoryCriteria setId(Long id) {
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
	@QueryParam("questionId")
	public AnswerCategoryCriteria setQuestionId(Long questionId) {
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
	@QueryParam("name")
	public AnswerCategoryCriteria setName(String name) {
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
	@QueryParam("numero")
	public AnswerCategoryCriteria setNumero(Integer numero) {
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
	@QueryParam("timestamps")
	public AnswerCategoryCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
	
}