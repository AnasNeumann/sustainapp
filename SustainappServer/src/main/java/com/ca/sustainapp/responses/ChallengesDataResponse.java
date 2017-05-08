package com.ca.sustainapp.responses;

import java.util.HashMap;
import java.util.Map;

/**
 * Json de réponse pour l'affichage des data sur les challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
public class ChallengesDataResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private Integer average;
	private Map<String, Integer> coursesByCategories = new HashMap<String, Integer>();
	private Map<String, Integer> useByHours = new HashMap<String, Integer>();

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public ChallengesDataResponse setTotal(Integer total) {
		this.total = total;
		return this;
	}

	/**
	 * @return the average
	 */
	public Integer getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public ChallengesDataResponse setAverage(Integer average) {
		this.average = average;
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
	public ChallengesDataResponse setCoursesByCategories(Map<String, Integer> coursesByCategories) {
		this.coursesByCategories = coursesByCategories;
		return this;
	}

	/**
	 * @return the useByHours
	 */
	public Map<String, Integer> getUseByHours() {
		return useByHours;
	}

	/**
	 * @param useByHours the useByHours to set
	 */
	public ChallengesDataResponse setUseByHours(Map<String, Integer> useByHours) {
		this.useByHours = useByHours;
		return this;
	}
}