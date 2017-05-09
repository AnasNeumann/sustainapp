package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json de réponse pour l'affichage des data sur les recherches
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
public class ResearchDataResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer total;
	private Map<String, Integer> useByHours = new HashMap<String, Integer>();
	private Map<String, Integer> useByDays =  new HashMap<String, Integer>();
	private List<String> mostSeen = new ArrayList<String>();

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public ResearchDataResponse setTotal(Integer total) {
		this.total = total;
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
	public ResearchDataResponse setUseByHours(Map<String, Integer> useByHours) {
		this.useByHours = useByHours;
		return this;
	}
	
	/**
	 * @return the mostSeen
	 */
	public List<String> getMostSeen() {
		return mostSeen;
	}
	
	/**
	 * @param mostSeen the mostSeen to set
	 */
	public ResearchDataResponse setMostSeen(List<String> mostSeen) {
		this.mostSeen = mostSeen;
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
	public ResearchDataResponse setUseByDays(Map<String, Integer> useByDays) {
		this.useByDays = useByDays;
		return this;
	}
}