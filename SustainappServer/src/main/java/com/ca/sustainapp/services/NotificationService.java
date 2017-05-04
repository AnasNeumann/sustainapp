package com.ca.sustainapp.services;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.controllers.NotificationController;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.NotificationServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.NotificationEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.responses.NotificationResponse;

/**
 * Service pour la création des notifications
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/05/2017
 * @verion 1.0
 */
@Service("notificationBusinessService")
public class NotificationService {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	protected NotificationServiceDAO notificationService;
	@Autowired
	protected ProfileServiceDAO profilService;
	@Autowired
	protected TeamServiceDAO teamService;
	@Autowired
	protected ParticipationServiceDAO participationService;
	@Autowired
	protected CourseServiceDAO coursService;
	@Autowired
	protected ChallengeServiceDAO challengeService;
	
	/**
	 * Pour l'envoi en websocket de la nouvelle notification
	 */
	@Autowired
    private SimpMessagingTemplate template;
	
	/**
	 * Créer une notification en base de données sans préciser le type de profil qui crée
	 * @param code
	 * @param profilId
	 * @param creatorId
	 * @param linkId
	 */
	public NotificationEntity create(String code, Long profilId, Long creatorId, Long linkId){
		return create(code, profilId, creatorId, linkId, SustainappConstantes.NOTIFICATION_CREATORTYPE_PROFILE);
	}
	
	/**
	 * Créer une notification en base de données
	 * @param code
	 * @param profilId
	 * @param creatorId
	 * @param linkId
	 * @param creatorType
	 */
	public NotificationEntity create(String code, Long profilId, Long creatorId, Long linkId, Integer creatorType){
		NotificationEntity notification = new NotificationEntity()
				.setCreatorId(creatorId)
				.setProfilId(profilId)
				.setMessage(code)
				.setCreatorType(creatorType)
				.setLinkId(linkId)
				.setState(0)	
				.setTimestamps(GregorianCalendar.getInstance());
		notification.setId(notificationService.createOrUpdate(notification));
		sendWebSocket(build(notification));	
		return notification;
	}
	
	/**
	 * Methode permettant d'appeler la méthode de websocket pour envoyer la notification
	 * @param notification
	 */
	private void sendWebSocket(NotificationResponse notification){
		if(null != notification){
			template.convertAndSend("/websocket/notification", notification);
		}
	}
	
	/**
	 * Construire une notification affichable à partir d'une notification en base de données
	 * @param entity
	 * @return
	 */
	public NotificationResponse build(NotificationEntity entity){
		NotificationResponse notification = new NotificationResponse(entity);
		boolean needCreator = false;
		switch(entity.getMessage()){
			case SustainappConstantes.NOTIFICATION_MESSAGE_PARTICIPATE :
				needCreator = true;
				ChallengeEntity challenge = challengeService.getById(entity.getLinkId());
				if(null == challenge){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setTarget(challenge.getName());
				notification.setLink("challenges/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_RANK : 
				CourseEntity cours =coursService.getById(entity.getLinkId());
				if(null == cours){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setTarget(cours.getTitle());
				notification.setLink("cours/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_VOTE : 
				ParticipationEntity participation = participationService.getById(entity.getLinkId());
				if(null == participation){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setTarget(participation.getTitle());
				notification.setLink("challenges/"+participation.getChallengeId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_ACCEPT :
				TeamEntity team = teamService.getById(entity.getLinkId());
				if(null == team){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setTarget(team.getName());
				notification.setLink("team/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_REQUEST :
				needCreator = true;
				TeamEntity team1 = teamService.getById(entity.getLinkId());
				if(null == team1){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setTarget(team1.getName());
				notification.setLink("team/"+entity.getLinkId());
				break;			
			case SustainappConstantes.NOTIFICATION_MESSAGE_BADGE:
				notification.setLink("profile/"+entity.getLinkId());
				notification.setTarget("");
				break;
		}
		if(needCreator){
			if(entity.getCreatorType().equals(SustainappConstantes.NOTIFICATION_CREATORTYPE_PROFILE)){
				ProfileEntity creator = profilService.getById(entity.getCreatorId());
				if(null == creator){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setCreator(creator.getFirstName()+" "+creator.getLastName());
			}else{
				TeamEntity team2 = teamService.getById(entity.getCreatorId());
				if(null == team2){
					notificationService.delete(notification.getId());
					return null;
				}
				notification.setCreator(team2.getName());
			}
		}else{
			notification.setCreator("");
		}
		return notification;
	}
}