package com.ca.sustainapp.controllers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ca.sustainapp.comparators.EntityComparator;
import com.ca.sustainapp.comparators.NumerotableEntityComparator;
import com.ca.sustainapp.dao.UserAccountServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.NotificationResponse;
import com.ca.sustainapp.services.BadgeService;
import com.ca.sustainapp.services.CascadeDeleteService;
import com.ca.sustainapp.services.CascadeGetService;
import com.ca.sustainapp.services.NotificationService;
import com.ca.sustainapp.utils.DateUtils;
import com.ca.sustainapp.utils.StringsUtils;


/**
 * Mother class of all controller
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
@Controller
@CrossOrigin
public class GenericController {
	
	/**
	 * Injection de dépendances DAO
	 */
	@Autowired
	protected UserAccountServiceDAO userService;
	

	/**
	 * Comparator
	 */
	@Autowired
	protected EntityComparator compartor;
	@Autowired
	protected NumerotableEntityComparator comparatorByNumber;
	
	/**
	 * Business Services
	 */
	@Autowired
	protected CascadeGetService getService;
	@Autowired
	protected CascadeDeleteService deleteService;
	@Autowired
	protected BadgeService badgeService;
	@Autowired
	protected NotificationService notificationService;
	
	/**
	 * Pour l'envoi en websocket de la notification d'un nouveau token
	 */
	@Autowired
    private SimpMessagingTemplate template;
	
	/**
	 * Creer une nouvelle session pour un utilisateur
	 * @param request
	 * @return
	 */
	protected String createSession(UserAccountEntity user){	
		String token = generateSessionToken();
		userService.createOrUpdate(user.setToken(token).setTokenDelay(GregorianCalendar.getInstance()));
		return token;
	}

	/**
	 * Recuperer la session d'un utilisateur
	 * @param userId
	 * @param token
	 * @return
	 */
	protected UserAccountEntity getConnectedUser(Long userId, String token){
		UserAccountEntity user = userService.getByToken(userId, token);
		if(null != user && DateUtils.verifyDelay(user.getTokenDelay(), 10)){
			createSession(user);
			template.convertAndSend("/queue/notification-"+user.getProfile().getId(), new NotificationResponse("token.refresh"));
		}
		return user;
	}
	
	/**
	 * Recuperer la session d'un utilisateur sans verification
	 * @param request
	 * @return
	 */
	public UserAccountEntity getUser(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		if(!id.isPresent()){
			return null;
		}
		return userService.getById(id.get());
	}
	
	/**
	 * Recuperer la session d'un utilisateur
	 * @param request
	 * @return
	 */
	public UserAccountEntity getConnectedUser(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return null;
		}
		return userService.getByToken(id.get(), token);
	}
	
	/**
	 * Verifier si un user est connecté
	 * @param userId
	 * @param token
	 * @return
	 */
	protected boolean isConnected(Long userId, String token){
		return getConnectedUser(userId, token) != null;
	}
	
	/**
	 * Verifier si un user est connecté
	 * @param request
	 * @return
	 */
	protected boolean isConnected(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return false;
		}
		return getConnectedUser(id.get(), token) != null;
	}
	
	/**
	 * Verifier si un user est administrateur
	 * @param request
	 * @return
	 */
	public boolean isAdmin(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return false;
		}
		return getConnectedUser(id.get(), token).getIsAdmin();
	}

	/**
	 *  Verifier si un user est administrateur
	 * @param userId
	 * @param token
	 * @return
	 */
	protected boolean isAdmin(Long userId, String token){
		return getConnectedUser(userId, token).getIsAdmin();
	}
	
	/**
	 * Supprimer une session lors de la deconnexion
	 * @param request
	 */
	protected boolean deleteSession(Long userId, String token){
		if(isConnected(userId, token)){
			return true;
		}
		return false;
	}
	
	/**
	 * Methode permettant de générer un token aléatoire pour la session
	 * @return
	 */
	private String generateSessionToken(){
		return StringsUtils.md5Hash(new BigInteger(130, new SecureRandom()).toString());
	}
	
	/**
	 * Get a profile by userId
	 * @param id
	 * @return
	 */
	protected ProfileEntity getProfileByUser(Long id){
		UserAccountEntity user = userService.getById(id);
		if(null == user){
			return null;
		}
		return user.getProfile();
	}
}