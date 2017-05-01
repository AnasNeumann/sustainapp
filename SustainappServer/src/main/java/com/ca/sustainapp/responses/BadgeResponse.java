package com.ca.sustainapp.responses;

import com.ca.sustainapp.entities.BadgeEntity;

/**
 * Json de réponse d'un badge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/04/2017
 * @version 1.0
 */
public class BadgeResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private BadgeEntity badge;
	private Boolean on;
	
	/**
	 * @return the badge
	 */
	public BadgeEntity getBadge() {
		return badge;
	}
	
	/**
	 * @param badge the badge to set
	 */
	public BadgeResponse setBadge(BadgeEntity badge) {
		this.badge = badge;
		return this;
	}
	
	/**
	 * @return the on
	 */
	public Boolean getOn() {
		return on;
	}
	
	/**
	 * @param on the on to set
	 */
	public BadgeResponse setOn(Boolean on) {
		this.on = on;
		return this;
	}
}
