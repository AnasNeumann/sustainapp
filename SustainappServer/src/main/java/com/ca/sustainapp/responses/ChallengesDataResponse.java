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
	private Float average;
	private Map<String, Integer> challengesByCategories = new HashMap<String, Integer>();
	private Map<String, Integer> useByHours = new HashMap<String, Integer>();
	private Map<String, Integer> useByDays =  new HashMap<String, Integer>();

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
	public Float getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public ChallengesDataResponse setAverage(Float average) {
		this.average = average;
		return this;
	}

	/**
	 * @return the coursesByCategories
	 */
	public Map<String, Integer> getChallengesByCategories() {
		return challengesByCategories;
	}

	/**
	 * @param coursesByCategories the coursesByCategories to set
	 */
	public ChallengesDataResponse setChallengesByCategories(Map<String, Integer> challengesByCategories) {
		this.challengesByCategories = challengesByCategories;
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

	/**
	 * @return the useByDays
	 */
	public Map<String, Integer> getUseByDays() {
		return useByDays;
	}

	/**
	 * @param useByDays the useByDays to set
	 */
	public ChallengesDataResponse setUseByDays(Map<String, Integer> useByDays) {
		this.useByDays = useByDays;
		return this;
	}
}