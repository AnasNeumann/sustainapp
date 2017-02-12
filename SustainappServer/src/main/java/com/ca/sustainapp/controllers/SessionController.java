package com.ca.sustainapp.controllers;

import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.ProfileCriteria;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.SessionResponse;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.LoginValidator;
import com.ca.sustainapp.validators.SigninValidator;

/**
 * Restfull controller for session management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class SessionController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private SigninValidator signinValidator;
	@Autowired
	private LoginValidator loginValidator;
	
	/**
	 * create a new account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/signin", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String singin(HttpServletRequest request) {
		SessionResponse response = new SessionResponse();
		response.setErrors(signinValidator.validate(request));
		if(null != response.getErrors() && response.getErrors().size() > 0){
			response.setCode(0);
			return response.buildJson();
		}
		ProfileEntity profile = new ProfileEntity()
				.setMail(request.getParameter("mail"))
				.setPassword(StringsUtils.md5Hash(request.getParameter("password")))
				.setFirstName(request.getParameter("firstName"))
				.setLastName(request.getParameter("lastName"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setIsAdmin(false);
		response.setCode(1);
		profile.setId(profilService.createOrUpdate(profile));
		super.reloadSession(request, profile.getId());
		return response.setProfile(profile).buildJson();
    }

	/**
	 * login a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String login(HttpServletRequest request) {
		SessionResponse response = new SessionResponse();
		response.setErrors(loginValidator.validate(request));
		if(null != response.getErrors() && response.getErrors().size() > 0){
			response.setCode(0);
			return response.buildJson();
		}
		ProfileEntity profile = existAccount(request.getParameter("mail"), request.getParameter("password"));
		if(null == profile){
			response.setCode(0);
			response.getErrors().put("mail", "form.mail.matching");
			return response.buildJson();
		}
		super.reloadSession(request, profile.getId());
		response.setCode(1);
		return response.setProfile(profile).buildJson();
    }

	/**
	 * logout a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new HttpRESTfullResponse().setCode(1).buildJson();
    }

	/**
	 * get a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/session", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String verifySession(HttpServletRequest request) {
		SessionResponse response = new SessionResponse();
		if(super.isConnected(request)){
			response.setCode(1);
			response.setProfile(super.getSession(request));
		} else {
			response.setCode(0);
		}
		return response.buildJson();
    }
	
	/**
	 * Verifier si le compte existe bien
	 * @param mail
	 * @param passowrd
	 * @return
	 */
	private ProfileEntity existAccount(String mail, String passowrd){
		SearchResult<ProfileEntity> listResult = profilService.searchByCriteres(new ProfileCriteria().setMail(mail), 0L, 100L);
		if(null != listResult.getResults()){
			for(ProfileEntity profile : listResult.getResults()){
				if(profile.getMail().equals(mail) && profile.getPassword().equals(StringsUtils.md5Hash(passowrd))){
					return profile;
				}
			}
		}
		return null;
	}

}