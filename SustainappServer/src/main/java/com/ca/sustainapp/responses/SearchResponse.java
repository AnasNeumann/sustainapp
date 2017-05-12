package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'une recherche
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
public class SearchResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<ProfileEntity> profiles = new SustainappList<ProfileEntity>();
	private List<TeamEntity> teams = new SustainappList<TeamEntity>();
	private List<LightCourseResponse> courses = new SustainappList<LightCourseResponse>();
	private List<LightCityResponse> cities = new SustainappList<LightCityResponse>();

	/**
	 * @return the profiles
	 */
	public List<ProfileEntity> getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles
	 *            the profiles to set
	 */
	public SearchResponse setProfiles(List<ProfileEntity> profiles) {
		this.profiles = profiles;
		return this;
	}

	/**
	 * @return the teams
	 */
	public List<TeamEntity> getTeams() {
		return teams;
	}

	/**
	 * @param teams
	 *            the teams to set
	 */
	public SearchResponse setTeams(List<TeamEntity> teams) {
		this.teams = teams;
		return this;
	}

	/**
	 * @return the courses
	 */
	public List<LightCourseResponse> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public SearchResponse setCourses(List<LightCourseResponse> courses) {
		this.courses = courses;
		return this;
	}

	/**
	 * @return the cities
	 */
	public List<LightCityResponse> getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public SearchResponse setCities(List<LightCityResponse> cities) {
		this.cities = cities;
		return this;
	}
}