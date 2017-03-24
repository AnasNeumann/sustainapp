package com.ca.sustainapp.responses;

import java.util.Calendar;

/**
 * Json de réponse d'un vote
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/03/2017
 * @version 1.0
 */
public class VoteResponse {
	LightProfileResponse profile;
	Calendar timestamps;

	/**
	 * @return the profile
	 */
	public LightProfileResponse getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public VoteResponse setProfile(LightProfileResponse profile) {
		this.profile = profile;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public VoteResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}