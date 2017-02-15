package com.ca.sustainapp.controllers;

import java.util.Map;
import java.util.Optional;

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
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.ProfileResponse;
import com.ca.sustainapp.utils.DateUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ProfileValidator;

/**
 * Restfull controller for profile management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/02/2017 <3
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ProfileController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private ProfileValidator profileValidator;
	
	/**
	 * get a profile by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		ProfileEntity profile = profileService.getById(id.get());
		if(null == profile){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new ProfileResponse().setProfiles(new SustainappList<ProfileEntity>().put(profile)).setCode(1).buildJson();
	}
	
	/**
	 * update a profile by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String update(HttpServletRequest request){
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("sessionId"));
		String token = request.getParameter("sessionToken");
		Map<String, String> errors = profileValidator.validate(request);
		if(!id.isPresent() || null == token || (null != errors && errors.size() > 0)){
			return new HttpRESTfullResponse().setCode(0).setErrors(errors).buildJson(); 
		}
		UserAccountEntity user = getConnectedUser(id.get(), token);
		if(null != user && null != user.getProfile()){
			user.getProfile()
				.setLastName(request.getParameter("lastName"))
				.setFirstName(request.getParameter("firstName"))
				.setBornDate(DateUtils.ionicParse(request.getParameter("bornDate"), user.getProfile().getBornDate()));
			userService.createOrUpdate(user);
			return new HttpRESTfullResponse().setCode(1).buildJson(); 
		}
		return new HttpRESTfullResponse().setCode(0).buildJson(); 
	}
	
	/**
	 * update a cover by profile id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/cover", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String cover(){
		return null;
	}
	
	/**
	 * update an avatar by profile id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/avatar", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String avatar(){
		return null;
	}
}
