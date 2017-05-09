package com.ca.sustainapp.responses;

import java.util.HashMap;
import java.util.Map;

/**
 * Json de réponse pour l'affichage des data sur les profils et équipes
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
public class ProfilesDataResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Integer totalTeams;
	private Integer totalProfiles;
	private Float average;
	private Map<Integer, Integer> teamByLevel = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> profileByLevel = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> profileByAge = new HashMap<Integer, Integer>();
	
	/**
	 * @return the totalTeams
	 */
	public Integer getTotalTeams() {
		return totalTeams;
	}

	/**
	 * @param totalTeams the totalTeams to set
	 */
	public ProfilesDataResponse setTotalTeams(Integer totalTeams) {
		this.totalTeams = totalTeams;
		return this;
	}

	/**
	 * @return the totalProfiles
	 */
	public Integer getTotalProfiles() {
		return totalProfiles;
	}

	/**
	 * @param totalProfiles the totalProfiles to set
	 */
	public ProfilesDataResponse setTotalProfiles(Integer totalProfiles) {
		this.totalProfiles = totalProfiles;
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
	public ProfilesDataResponse setAverage(Float average) {
		this.average = average;
		return this;
	}

	/**
	 * @return the teamByLevel
	 */
	public Map<Integer, Integer> getTeamByLevel() {
		return teamByLevel;
	}

	/**
	 * @param teamByLevel the teamByLevel to set
	 */
	public ProfilesDataResponse setTeamByLevel(Map<Integer, Integer> teamByLevel) {
		this.teamByLevel = teamByLevel;
		return this;
	}

	/**
	 * @return the profileByLevel
	 */
	public Map<Integer, Integer> getProfileByLevel() {
		return profileByLevel;
	}

	/**
	 * @param profileByLevel the profileByLevel to set
	 */
	public ProfilesDataResponse setProfileByLevel(Map<Integer, Integer> profileByLevel) {
		this.profileByLevel = profileByLevel;
		return this;
	}

	/**
	 * @return the profileByAge
	 */
	public Map<Integer, Integer> getProfileByAge() {
		return profileByAge;
	}

	/**
	 * @param profileByAge the profileByAge to set
	 */
	public ProfilesDataResponse setProfileByAge(Map<Integer, Integer> profileByAge) {
		this.profileByAge = profileByAge;
		return this;
	}
}