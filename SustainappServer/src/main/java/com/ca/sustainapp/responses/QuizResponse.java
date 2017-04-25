package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse complète d'un quiz
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
public class QuizResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<QuestionQuizResponse> questions = new SustainappList<QuestionQuizResponse>();
	
	/**
	 * @return the questions
	 */
	public List<QuestionQuizResponse> getQuestions() {
		return questions;
	}
	
	/**
	 * @param questions the questions to set
	 */
	public QuizResponse setQuestions(List<QuestionQuizResponse> questions) {
		this.questions = questions;
		return this;
	}
}
