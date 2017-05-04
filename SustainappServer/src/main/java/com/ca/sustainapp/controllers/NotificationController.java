package com.ca.sustainapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.NotificationCriteria;
import com.ca.sustainapp.dao.NotificationServiceDAO;
import com.ca.sustainapp.entities.NotificationEntity;
import com.ca.sustainapp.entities.NotificationsResponse;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.NotificationResponse;
import com.ca.sustainapp.utils.ListUtils;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Restfull controller pour l'affichage et ajout de notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class NotificationController extends GenericController{
	
	
	/**
	 * Injection de dépendances
	 */
	@Autowired
	private NotificationServiceDAO notificationServiceDAO;

	/**
	 * Recevoir toutes les dernières notifications et passer toutes les 0(reçue) à 1(vue)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification/all", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String getAll(HttpServletRequest request){
		if(!isConnected(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<NotificationEntity> listResult = ListUtils.reverseList(getService.cascadeGetNotifications(new NotificationCriteria().setProfilId(super.getConnectedUser(request).getProfile().getId())));
		List<NotificationResponse> notifications = new SustainappList<NotificationResponse>();
		int maxResult = 15;
		for(NotificationEntity entity : listResult){
			if(maxResult > 0){
				if(entity.getState().equals(0)){
					notificationServiceDAO.createOrUpdate(entity.setState(1));
				}
				NotificationResponse notification  = notificationService.build(entity);
				if(null != notification){
					notifications.add(notification);				
					maxResult--;
				}			
			}else{
				notificationServiceDAO.delete(entity.getId());
			}
		}
		return new NotificationsResponse()
				.setNotifications(notifications)
				.setCode(1)
				.buildJson();
	}

	/**
	 * passer une notification de vue(1) à cliquée(2)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/notification/read", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String read(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> idNotification = StringsUtils.parseLongQuickly(request.getParameter("notification"));
		if(null == user || !idNotification.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		NotificationEntity notification = notificationServiceDAO.getById(idNotification.get());
		if(null == notification || !notification.getProfilId().equals(user.getProfile().getId())){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		notificationServiceDAO.createOrUpdate(notification.setState(2));
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}

	/**
	 * Recevoir une nouvelle notification
	 * => via STOMP Temps réel / websockets
	 * @param request
	 * @return
	 */
	@MessageMapping("/notification")
    @SendTo("/websocket/notification")
	public NotificationResponse send(NotificationEntity notification){
		NotificationResponse result = notificationService.build(notification);
		return result;
	}
	
}