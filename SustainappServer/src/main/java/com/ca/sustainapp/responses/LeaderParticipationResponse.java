package com.ca.sustainapp.responses;

/**
 * Json de réponse pour l'affichage d'une participation dans le journal
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
public class LeaderParticipationResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private LightProfileResponse owner;
	private byte[] document;
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
	public LeaderParticipationResponse setOwner(LightProfileResponse owner) {
		this.owner = owner;
		return this;
	}
	
	/**
	 * @return the document
	 */
	public byte[] getDocument() {
		return document;
	}
	
	/**
	 * @param document the document to set
	 */
	public LeaderParticipationResponse setDocument(byte[] document) {
		this.document = document;
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
	public LeaderParticipationResponse setLink(String link) {
		this.link = link;
		return this;
	}
}