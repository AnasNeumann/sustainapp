package com.ca.sustainapp.responses;

/**
 * Json de réponse pour l'affichage d'un cours dans le journal
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
public class LeaderCoursResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private LightProfileResponse owner;
	private String name;
	private String link;
	
	/**
	 * @return the owner
	 */
	public LightProfileResponse getOwner() {
		return owner;
	}
	
	/**
	 * @param owner the owner to set
	 */
	public LeaderCoursResponse setOwner(LightProfileResponse owner) {
		this.owner = owner;
		return this;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public LeaderCoursResponse setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * @param link the link to set
	 */
	public LeaderCoursResponse setLink(String link) {
		this.link = link;
		return this;
	}
}