package com.ca.sustainapp.responses;

import com.ca.sustainapp.entities.ProfileEntity;

/**
 * Json de réponse lors d'une tentative d'inscription l'inscription
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/02/2017
 * @version 1.0
 */
public class SessionResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private ProfileEntity profile;
	private Long id;
	private String token;
	
	/**
	 * @return the profile
	 */
	public ProfileEntity getProfile() {
		return profile;
	}
	
	/**
	 * @param profile the profile to set
	 */
	public SessionResponse setProfile(ProfileEntity profile) {
		this.profile = profile;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public SessionResponse setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public SessionResponse setToken(String token) {
		this.token = token;
		return this;
	}
}