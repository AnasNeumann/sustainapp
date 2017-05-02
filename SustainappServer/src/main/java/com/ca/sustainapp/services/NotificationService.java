package com.ca.sustainapp.services;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.NotificationServiceDAO;
import com.ca.sustainapp.entities.NotificationEntity;

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
}