package com.ca.sustainapp.services;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.NotificationServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
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
	 * Créer une notification en base de données sans préciser le type de profil qui crée
	 * @param code
	 * @param profilId
	 * @param creatorId
	 * @param linkId
	 */
	public void create(String code, Long profilId, Long creatorId, Long linkId){
		create(code, profilId, creatorId, linkId, SustainappConstantes.NOTIFICATION_CREATORTYPE_PROFILE);
	}
	
	/**
	 * Créer une notification en base de données
	 * @param code
	 * @param profilId
	 * @param creatorId
	 * @param linkId
	 * @param creatorType
	 */
	public void create(String code, Long profilId, Long creatorId, Long linkId, Integer creatorType){
		notificationService.createOrUpdate(
				new NotificationEntity()
					.setCreatorId(creatorId)
					.setProfilId(profilId)
					.setMessage(code)
					.setCreatorType(creatorType)
					.setLinkId(linkId)
					.setState(0)	
					.setTimestamps(GregorianCalendar.getInstance()));
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
				notification.setTarget(challengeService.getById(entity.getLinkId()).getName());
				notification.setLink("challenges/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_RANK : 
				notification.setTarget(coursService.getById(entity.getLinkId()).getTitle());
				notification.setLink("cours/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_VOTE : 
				ParticipationEntity participation = participationService.getById(entity.getLinkId());
				notification.setTarget(participation.getTitle());
				notification.setLink("challenges/"+participation.getChallengeId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_ACCEPT :
				notification.setTarget(teamService.getById(entity.getLinkId()).getName());
				notification.setLink("team/"+entity.getLinkId());
				break;
			case SustainappConstantes.NOTIFICATION_MESSAGE_REQUEST :
				needCreator = true;
				notification.setTarget(teamService.getById(entity.getLinkId()).getName());
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
				notification.setCreator(creator.getFirstName()+" "+creator.getLastName());
			}else{
				TeamEntity team = teamService.getById(entity.getCreatorId());
				notification.setCreator(team.getName());
			}
		}else{
			notification.setCreator("");
		}
		return notification;
	}
}