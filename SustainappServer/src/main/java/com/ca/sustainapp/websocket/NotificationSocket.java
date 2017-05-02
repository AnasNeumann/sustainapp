package com.ca.sustainapp.websocket;

/**
 * Message de notification par websocket
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/05/2017
 * @version 1.0
 */
public class NotificationSocket {
	private Integer id;
	private String message;
	private String link;
	private String targetName;
	private String creatorDenomination;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public NotificationSocket setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public NotificationSocket setMessage(String message) {
		this.message = message;
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
	public NotificationSocket setLink(String link) {
		this.link = link;
		return this;
	}

	/**
	 * @return the targetName
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param targetName the targetName to set
	 */
	public NotificationSocket setTargetName(String targetName) {
		this.targetName = targetName;
		return this;
	}

	/**
	 * @return the creatorDenomination
	 */
	public String getCreatorDenomination() {
		return creatorDenomination;
	}

	/**
	 * @param creatorDenomination the creatorDenomination to set
	 */
	public NotificationSocket setCreatorDenomination(String creatorDenomination) {
		this.creatorDenomination = creatorDenomination;
		return this;
	}
}