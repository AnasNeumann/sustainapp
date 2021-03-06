package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class AnswerCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long questionId;
	private String message;
	private Calendar timestamps;
	private String data;
	
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
	public AnswerCriteria setId(Long id) {
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
	public AnswerCriteria setQuestionId(Long questionId) {
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
	@QueryParam("message")
	public AnswerCriteria setMessage(String message) {
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
	@QueryParam("timestamps")
	public AnswerCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	@QueryParam("data")
	public AnswerCriteria setData(String data) {
		this.data = data;
		return this;
	}
	
	
}