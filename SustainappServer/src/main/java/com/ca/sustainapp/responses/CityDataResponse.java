package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ca.sustainapp.entities.PlaceEntity;

/**
 * Json de réponse pour l'affichage des data sur les profils et équipes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/05/2017
 * @version 1.0
 */
public class CityDataResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer totalCities;
	private Integer totalPlaces;
	private Float averagePictures;
	private Map<Integer, Integer> placeByNotes = new HashMap<Integer, Integer>();
	private List<PlaceEntity> moreViews = new ArrayList<PlaceEntity>();
	private Map<String, Integer> visitByHours = new HashMap<String, Integer>();
	private Map<String, Integer> visitByDays =  new HashMap<String, Integer>();

	/**
	 * @return the totalCities
	 */
	public Integer getTotalCities() {
		return totalCities;
	}

	/**
	 * @param totalCities the totalCities to set
	 */
	public CityDataResponse setTotalCities(Integer totalCities) {
		this.totalCities = totalCities;
		return this;
	}

	/**
	 * @return the totalPlaces
	 */
	public Integer getTotalPlaces() {
		return totalPlaces;
	}

	/**
	 * @param totalPlaces the totalPlaces to set
	 */
	public CityDataResponse setTotalPlaces(Integer totalPlaces) {
		this.totalPlaces = totalPlaces;
		return this;
	}

	/**
	 * @return the averagePictures
	 */
	public Float getAveragePictures() {
		return averagePictures;
	}

	/**
	 * @param averagePictures the averagePictures to set
	 */
	public CityDataResponse setAveragePictures(Float averagePictures) {
		this.averagePictures = averagePictures;
		return this;
	}

	/**
	 * @return the placeByNotes
	 */
	public Map<Integer, Integer> getPlaceByNotes() {
		return placeByNotes;
	}

	/**
	 * @param placeByNotes the placeByNotes to set
	 */
	public CityDataResponse setPlaceByNotes(Map<Integer, Integer> placeByNotes) {
		this.placeByNotes = placeByNotes;
		return this;
	}

	/**
	 * @return the moreViews
	 */
	public List<PlaceEntity> getMoreViews() {
		return moreViews;
	}

	/**
	 * @param moreViews the moreViews to set
	 */
	public CityDataResponse setMoreViews(List<PlaceEntity> moreViews) {
		this.moreViews = moreViews;
		return this;
	}

	/**
	 * @return the visitByHours
	 */
	public Map<String, Integer> getVisitByHours() {
		return visitByHours;
	}

	/**
	 * @param visitByHours the visitByHours to set
	 */
	public CityDataResponse setVisitByHours(Map<String, Integer> visitByHours) {
		this.visitByHours = visitByHours;
		return this;
	}

	/**
	 * @return the visitByDays
	 */
	public Map<String, Integer> getVisitByDays() {
		return visitByDays;
	}

	/**
	 * @param visitByDays the visitByDays to set
	 */
	public CityDataResponse setVisitByDays(Map<String, Integer> visitByDays) {
		this.visitByDays = visitByDays;
		return this;
	}
}