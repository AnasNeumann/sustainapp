package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.List;

import com.ca.sustainapp.entities.PlaceEntity;

/**
 * Json de réponse pour l'affichage léger d'une liste d'écolieu
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 16/05/2017
 * @version 1.0
 */
public class PlacesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<PlaceEntity> places = new ArrayList<PlaceEntity>();

	/**
	 * @return the places
	 */
	public List<PlaceEntity> getPlaces() {
		return places;
	}

	/**
	 * @param places the places to set
	 */
	public PlacesResponse setPlaces(List<PlaceEntity> places) {
		this.places = places;
		return this;
	}
}