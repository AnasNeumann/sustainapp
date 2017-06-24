package com.ca.sustainapp.controllers;

import java.util.GregorianCalendar;
import java.util.Optional;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
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
	@Autowired
	private CityServiceDAO cityService;

	/**
	 * create a new account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/signin", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> singin(HttpServletRequest request) {
		SessionResponse response = new SessionResponse();
		response.setErrors(signinValidator.validate(request));
		if(null != response.getErrors() && response.getErrors().size() > 0){
			return super.refuse(signinValidator.validate(request));
		}
		Optional<Integer> type = StringsUtils.parseIntegerQuietly(request.getParameter("type"));
		response.setUserType(type.get());
		UserAccountEntity user = new  UserAccountEntity()
				.setMail(request.getParameter("mail"))
				.setPassword(StringsUtils.md5Hash(request.getParameter("password")))
				.setIsAdmin(false)
				.setType(type.get())
				.setTimestamps(GregorianCalendar.getInstance());
		Long idUser = super.userService.createOrUpdate(user);
		ProfileEntity profile = new ProfileEntity()
				.setUserId(idUser)
				.setFirstName(request.getParameter("firstName"))
				.setLastName(request.getParameter("lastName"))
				.setLevel(0)
				.setVisibility(1)
				.setTimestamps(GregorianCalendar.getInstance());
		profile.setId(profilService.createOrUpdate(profile));		
		if(type.get().equals(1)){
			CityEntity city = new CityEntity()
					.setName(request.getParameter("city"))
					.setPhone(request.getParameter("phone"))
					.setActif(0)
					.setTimestamps(GregorianCalendar.getInstance())
					.setUserId(idUser);
			city.setId(cityService.createOrUpdate(city));
			response.setCity(city);
		}
		return success(response
				.setId(idUser)
				.setToken(super.createSession(user.setProfile(profile)))
				.setProfile(profile));
    }

	/**
	 * login a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> login(HttpServletRequest request) {
		SessionResponse response = new SessionResponse();
		response.setErrors(loginValidator.validate(request));
		if(null != response.getErrors() && response.getErrors().size() > 0){
			return super.refuse(loginValidator.validate(request));
		}
		UserAccountEntity user = userService.connect(request.getParameter("mail"), StringsUtils.md5Hash(request.getParameter("password")));
		if(null == user){
			response.getErrors().put("mail", "form.mail.matching");
			return super.refuse(response.getErrors());
		}
		String token;
		if(!isEmpty(user.getToken())){
			token = user.getToken();
		}else{
			token = super.createSession(user);
		}
		if(user.getType().equals(1)){
			response.setCity(getService.cascadeGetCities(new CityCriteria().setUserId(user.getId())).get(0));
		}
		return super.success(response
				.setUserType(user.getType())
				.setId(user.getId())
				.setToken(token)
				.setProfile(user.getProfile())
				.setIsAdmin(user.getIsAdmin()));
    }

	/**
	 * logout a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> logout(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return super.refuse();
		}
		if(super.deleteSession(id.get(), token)){
			return super.success();
		}
		return super.refuse();
    }

	/**
	 * get a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/session", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> verifySession(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		if(!id.isPresent() || null == token){
			return super.refuse();
		}
		if(super.isConnected(id.get(), token)){ 
			return super.success(new SessionResponse()
					.setId(id.get())
					.setToken(token)
					.setProfile(super.getConnectedUser(id.get(), token).getProfile()));
		}
		return super.refuse();
    }
	
	/**
	 * refresh a session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/refresh", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> refresh(HttpServletRequest request) {
		UserAccountEntity user = userService.connect(request.getParameter("mail"), StringsUtils.md5Hash(request.getParameter("password")));
		if(null != user){
			return super.success(new SessionResponse()
					.setId(user.getId())
					.setToken(user.getToken())
					.setProfile(null));
		}
		return super.refuse();
	}
	
	
}