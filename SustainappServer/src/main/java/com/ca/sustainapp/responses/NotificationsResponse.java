package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'une liste de notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/05/2017
 * @version 1.0
 */
public class NotificationsResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<NotificationResponse> notifications = new SustainappList<NotificationResponse>();
	
	/**
	 * @return the notifications
	 */
	public List<NotificationResponse> getNotifications() {
		return notifications;
	}
	
	/**
	 * @param notifications the notifications to set
	 */
	public NotificationsResponse setNotifications(List<NotificationResponse> notifications) {
		this.notifications = notifications;
		return this;
	}
}