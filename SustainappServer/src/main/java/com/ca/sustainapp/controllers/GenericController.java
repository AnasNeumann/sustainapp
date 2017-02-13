package com.ca.sustainapp.controllers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ca.sustainapp.dao.UserAccountServiceDAO;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.utils.StringsUtils;


/**
 * Mother class of all controller
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
@Controller
public class GenericController {
	
	/**
	 * Injection de dépendances
	 */
	@Autowired
	protected UserAccountServiceDAO userService;

	/**
	 * Creer une nouvelle session pour un utilisateur
	 * @param request
	 * @return
	 */
	protected String createSession(UserAccountEntity user){	
		String token = generateSessionToken();
		userService.createOrUpdate(user.setToken(token));
		return token;
	}
	
	/**
	 * Recuperer la session d'un utilisateur
	 * @param request
	 * @return
	 */
	protected UserAccountEntity verifySession(Long userId, String token){
		return userService.getByToken(userId, token);
	}
	
	/**
	 * Verifier si un user est connecté
	 * @param userId
	 * @param token
	 * @return
	 */
	protected boolean isConnected(Long userId, String token){
		return verifySession(userId, token) != null;
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
		return verifySession(id.get(), token) != null;
	}
	
	/**
	 * Verifier si un user est administrateur
	 * @param request
	 * @return
	 */
	protected boolean isAdmin(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return false;
		}
		return verifySession(id.get(), token).getIsAdmin();
	}

	/**
	 *  Verifier si un user est administrateur
	 * @param userId
	 * @param token
	 * @return
	 */
	protected boolean isAdmin(Long userId, String token){
		return verifySession(userId, token).getIsAdmin();
	}
	
	/**
	 * Supprimer une session lors de la deconnexion
	 * @param request
	 */
	protected boolean deleteSession(Long userId, String token){
		if(isConnected(userId, token)){
			userService.createOrUpdate(userService.getById(userId).setToken(null));
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
}