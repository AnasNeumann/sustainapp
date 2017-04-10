package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'une ou plusieures questions d'un topic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/04/2017
 * @version 1.0
 */
public class QuestionsResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long courseId;
	private List<QuestionEntity> questions = new SustainappList<QuestionEntity>();
	
	/**
	 * @return the courseId
	 */
	public Long getCourseId() {
		return courseId;
	}
	
	/**
	 * @param courseId the courseId to set
	 */
	public QuestionsResponse setCourseId(Long courseId) {
		this.courseId = courseId;
		return this;
	}
	
	/**
	 * @return the questions
	 */
	public List<QuestionEntity> getQuestions() {
		return questions;
	}
	
	/**
	 * @param questions the questions to set
	 */
	public QuestionsResponse setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
		return this;
	}
}