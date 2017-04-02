package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour la reception de tous les cours en infinity scroll
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
public class CoursesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<CourseEntity> cours = new SustainappList<CourseEntity>();
	
	/**
	 * @return the cours
	 */
	public List<CourseEntity> getCours() {
		return cours;
	}
	
	/**
	 * @param cours the cours to set
	 */
	public CoursesResponse setCours(List<CourseEntity> cours) {
		this.cours = cours;
		return this;
	}
	
}