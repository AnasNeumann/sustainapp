package com.ca.sustainapp.responses;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.ca.sustainapp.entities.NotificationEntity;

/**
 * Json de réponse d'une notification
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/05/2017
 * @version 1.0
 */
public class NotificationResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String link;
	private String creator;
	private Integer state;
	private Calendar timestamps;
	private String target;
	private String message;
	
	/**
	 * Envoi avec message uniquement
	 * @param message
	 */
	public NotificationResponse(String message){
		this.message = message;
		this.timestamps = GregorianCalendar.getInstance();
		this.state = 0;
	}
	
	/**
	 * Constructor
	 * @param entity
	 */
	public NotificationResponse(NotificationEntity entity){
		this.id = entity.getId();
		this.state = entity.getState();
		this.timestamps  = entity.getTimestamps();
		this.message = entity.getMessage();
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
	public NotificationResponse setId(Long id) {
		this.id = id;
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
	public NotificationResponse setLink(String link) {
		this.link = link;
		return this;
	}
	
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * @param creator the creator to set
	 */
	public NotificationResponse setCreator(String creator) {
		this.creator = creator;
		return this;
	}
	
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public NotificationResponse setState(Integer state) {
		this.state = state;
		return this;
	}
	
	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}
	
	/**
	 * @param timestamps the timestamps to set
	 */
	public NotificationResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
	
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * @param target the target to set
	 */
	public NotificationResponse setTarget(String target) {
		this.target = target;
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
	public NotificationResponse setMessage(String message) {
		this.message = message;
		return this;
	}
}