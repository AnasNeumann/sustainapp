package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;

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
	protected ProfileServiceDAO profilService;

	/**
	 * Retrouver dans la session le profil actuellement connecté
	 * @param request
	 * @return
	 */
	protected ProfileEntity getSession(HttpServletRequest request){
		ProfileEntity session = (ProfileEntity) request.getSession().getAttribute("connectedProfile");
		return session;
	}
	
	/**
	 * Verifier si un utilisateur est connecté ou non
	 * @param request
	 * @return
	 */
	protected boolean isConnected(HttpServletRequest request){
		return null != getSession(request);
	}
	
	/**
	 * Reload a session
	 * @param request
	 * @param id
	 * @return
	 */
	protected ProfileEntity reloadSession(HttpServletRequest request, Long id){
		request.getSession().invalidate();
		request.getSession().setAttribute("connectedProfile", profilService.getById(id));
		return this.getSession(request);
	}
	
}