package com.ca.sustainapp.responses;

import com.ca.sustainapp.entities.CityEntity;

/**
 * Json de réponse d'une ville
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
public class CityResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private CityEntity city;
	private Boolean owner;

	/**
	 * @return the city
	 */
	public CityEntity getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public CityResponse setCity(CityEntity city) {
		this.city = city;
		return this;
	}

	/**
	 * @return the owner
	 */
	public Boolean getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public CityResponse setOwner(Boolean owner) {
		this.owner = owner;
		return this;
	}
}