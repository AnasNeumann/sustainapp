package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json de réponse pour l'affichage des data sur les cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
public class CoursesDataResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private Map<String, Integer> coursesByCategories = new HashMap<String, Integer>();
	private List<LightCourseResponse> mostSeen = new ArrayList<LightCourseResponse>();

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public CoursesDataResponse setTotal(Integer total) {
		this.total = total;
		return this;
	}

	/**
	 * @return the coursesByCategories
	 */
	public Map<String, Integer> getCoursesByCategories() {
		return coursesByCategories;
	}

	/**
	 * @param coursesByCategories the coursesByCategories to set
	 */
	public CoursesDataResponse setCoursesByCategories(Map<String, Integer> coursesByCategories) {
		this.coursesByCategories = coursesByCategories;
		return this;
	}

	/**
	 * @return the mostSeen
	 */
	public List<LightCourseResponse> getMostSeen() {
		return mostSeen;
	}

	/**
	 * @param mostSeen the mostSeen to set
	 */
	public CoursesDataResponse setMostSeen(List<LightCourseResponse> mostSeen) {
		this.mostSeen = mostSeen;
		return this;
	}
}