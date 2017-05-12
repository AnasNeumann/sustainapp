package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Json de réponse pour l'affichage des villes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/05/2017
 * @version 1.0
 */
public class CitiesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<LightCityResponse> cities = new ArrayList<LightCityResponse>();

	/**
	 * @return the cities
	 */
	public List<LightCityResponse> getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public CitiesResponse setCities(List<LightCityResponse> cities) {
		this.cities = cities;
		return this;
	}
}