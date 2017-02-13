package com.ca.sustainapp.controllers;

import java.util.GregorianCalendar;
import java.util.Optional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
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
public class UserAccountController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private SigninValidator signinValidator;
	@Autowired
	private LoginValidator loginValidator;
	@Autowired
	private ProfileServiceDAO profilService;
	
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
		UserAccountEntity user = new  UserAccountEntity()
				.setMail(request.getParameter("mail"))
				.setPassword(StringsUtils.md5Hash(request.getParameter("password")))
				.setIsAdmin(false)
				.setTimestamps(GregorianCalendar.getInstance());
		Long idUser = super.userService.createOrUpdate(user);
		ProfileEntity profile = new ProfileEntity()
				.setUserId(idUser)
				.setFirstName(request.getParameter("firstName"))
				.setLastName(request.getParameter("lastName"))
				.setTimestamps(GregorianCalendar.getInstance());
		profile.setId(profilService.createOrUpdate(profile));
		response.setCode(1);
		return response
			.setId(idUser)
			.setToken(super.createSession(user.setProfile(profile)))
			.setProfile(profile)
			.buildJson();
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
		UserAccountEntity user = userService.connect(request.getParameter("mail"), StringsUtils.md5Hash(request.getParameter("password")));
		if(null == user){
			response.setCode(0);
			response.getErrors().put("mail", "form.mail.matching");
			return response.buildJson();
		}
		String token;
		if(!isEmpty(user.getToken())){
			token = user.getToken();
		}else{
			token = super.createSession(user);
		}
		response.setCode(1);
		return response.setId(user.getId()).setToken(token).setProfile(user.getProfile()).buildJson();
    }

	/**
	 * logout a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String logout(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return new HttpRESTfullResponse().setCode(0).buildJson(); 
		}
		if(super.deleteSession(id.get(), token)){
			 return new HttpRESTfullResponse().setCode(1).buildJson(); 
		}
		return  new HttpRESTfullResponse().setCode(0).buildJson(); 
    }

	/**
	 * get a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/session", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String verifySession(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return new HttpRESTfullResponse().setCode(0).buildJson(); 
		}
		if(super.isConnected(id.get(), token)){
			SessionResponse response = new SessionResponse()
					.setId(id.get())
					.setToken(token)
					.setProfile(super.verifySession(id.get(), token).getProfile());
			response.setCode(1);
			return response.buildJson();
		}
		return new HttpRESTfullResponse().setCode(0).buildJson(); 
    }
}