package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'une question détaillée
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/04/2017
 * @version 1.0
 */
public class QuestionResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long courseId;
	private QuestionEntity question;
	private List<AnswerEntity> answers  = new SustainappList<AnswerEntity>();
	private List<AnswerCategoryEntity> categories  = new SustainappList<AnswerCategoryEntity>();
	
	/**
	 * @return the courseId
	 */
	public Long getCourseId() {
		return courseId;
	}
	
	/**
	 * @param courseId the courseId to set
	 */
	public QuestionResponse setCourseId(Long courseId) {
		this.courseId = courseId;
		return this;
	}
	
	/**
	 * @return the question
	 */
	public QuestionEntity getQuestion() {
		return question;
	}
	
	/**
	 * @param question the question to set
	 */
	public QuestionResponse setQuestion(QuestionEntity question) {
		this.question = question;
		return this;
	}
	
	/**
	 * @return the answers
	 */
	public List<AnswerEntity> getAnswers() {
		return answers;
	}
	
	/**
	 * @param answers the answers to set
	 */
	public QuestionResponse setAnswers(List<AnswerEntity> answers) {
		this.answers = answers;
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
	public QuestionResponse setCategories(List<AnswerCategoryEntity> categories) {
		this.categories = categories;
		return this;
	}
}