package com.ca.sustainapp.responses;

/**
 * Json de réponse lors d'un vote sur un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 01/04/2017
 * @version 1.0
 */
public class RankCoursResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private Float average;
	
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public RankCoursResponse setTotal(Integer total) {
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
	public RankCoursResponse setAverage(Float average) {
		this.average = average;
		return this;
	}
}
