package com.ca.sustainapp.responses;

import java.util.List;

/**
 * Json de réponse pour la validation d'un quiz
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/04/2017
 * @version 1.0
 */
public class ValidationResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Boolean allTrue;
	private List<Boolean> eachQuestions;
	
	/**
	 * @return the allTrue
	 */
	public Boolean getAllTrue() {
		return allTrue;
	}
	
	/**
	 * @param allTrue the allTrue to set
	 */
	public ValidationResponse setAllTrue(Boolean allTrue) {
		this.allTrue = allTrue;
		return this;
	}
	
	/**
	 * @return the eachQuestions
	 */
	public List<Boolean> getEachQuestions() {
		return eachQuestions;
	}
	
	/**
	 * @param eachQuestions the eachQuestions to set
	 */
	public ValidationResponse setEachQuestions(List<Boolean> eachQuestions) {
		this.eachQuestions = eachQuestions;
		return this;
	
	}	
}
