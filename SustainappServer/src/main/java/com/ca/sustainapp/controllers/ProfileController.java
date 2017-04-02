package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
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
import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.LightCourseResponse;
import com.ca.sustainapp.responses.ProfileResponse;
import com.ca.sustainapp.services.CascadeGetService;
import com.ca.sustainapp.utils.DateUtils;
import com.ca.sustainapp.utils.FilesUtils;
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
	@Autowired
	private CascadeGetService getService;
	
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
		return new ProfileResponse().setProfile(profile).setCourses(getAllProfileCourses(id.get())).setCode(1).buildJson();
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
	@RequestMapping(value="/profile/cover", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String cover(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);	
		if(null == user || null == user.getProfile() || isEmpty(request.getParameter("file"))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		user.getProfile().setCover(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		profileService.createOrUpdate(user.getProfile());
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * update an avatar by profile id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/avatar", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String avatar(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);	
		if(null == user || null == user.getProfile() || isEmpty(request.getParameter("file"))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		user.getProfile().setAvatar(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		profileService.createOrUpdate(user.getProfile());
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Get all courses made by a profile
	 * @param profilId
	 * @return
	 */
	private List<LightCourseResponse> getAllProfileCourses(Long profilId){
		List<CourseEntity> courses = getService.cascadeGetCourses(new CourseCriteria().setCreatorId(profilId));
		List<LightCourseResponse> result = new ArrayList<LightCourseResponse>();
		if(null != courses){
			for(CourseEntity course : courses){
				result.add(new LightCourseResponse(course));
			}
		}
		return result;
	}
}
