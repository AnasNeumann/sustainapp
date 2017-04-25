package com.ca.sustainapp.responses;

import java.util.Calendar;
import java.util.List;

import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage dans le quiz d'une question
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
public class QuestionQuizResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long topicId;
	private Integer type;
	private Integer numero;
	private byte[] picture;
	private String message;
	private Calendar timestamps;
	private List<AnswerCategoryEntity> categories = new SustainappList<AnswerCategoryEntity>();
	private List<AnswerResponse> answers = new SustainappList<AnswerResponse>();
	
	/**
	 * Constructeur à partir de l'entity
	 * @param entity
	 */
	public QuestionQuizResponse(QuestionEntity entity){
		this.id = entity.getId();
		this.topicId = entity.getTopicId();
		this.type = entity.getType();
		this.numero = entity.getNumero();
		this.picture = entity.getPicture();
		this.message = entity.getMessage();
		this.timestamps = entity.getTimestamps();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public QuestionQuizResponse setId(Long id) {
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
	public QuestionQuizResponse setTopicId(Long topicId) {
		this.topicId = topicId;
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
	public QuestionQuizResponse setType(Integer type) {
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
	public QuestionQuizResponse setNumero(Integer numero) {
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
	public QuestionQuizResponse setPicture(byte[] picture) {
		this.picture = picture;
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
	public QuestionQuizResponse setMessage(String message) {
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
	public QuestionQuizResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the categories
	 */
	public List<AnswerCategoryEntity> getCategories() {
		return categories;
	}
	
	/**
	 * @param categories the categories to set
	 */
	public QuestionQuizResponse setCategories(List<AnswerCategoryEntity> categories) {
		this.categories = categories;
		return this;
	}
	
	/**
	 * @return the answers
	 */
	public List<AnswerResponse> getAnswers() {
		return answers;
	}
	
	/**
	 * @param answers the answers to set
	 */
	public QuestionQuizResponse setAnswers(List<AnswerResponse> answers) {
		this.answers = answers;
		return this;
	}
}