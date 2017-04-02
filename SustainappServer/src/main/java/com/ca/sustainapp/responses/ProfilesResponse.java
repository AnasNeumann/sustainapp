package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'un ou plusieurs profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/02/2017
 * @version 1.0
 */
public class ProfilesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<ProfileEntity> profiles = new SustainappList<ProfileEntity>();
	
	/**
	 * @return the profile
	 */
	public List<ProfileEntity> getProfiles() {
		return profiles;
	}
	
	/**
	 * @param profile the profile to set
	 */
	public ProfilesResponse setProfiles(List<ProfileEntity> profiles) {
		this.profiles = profiles;
		return this;
	}

}