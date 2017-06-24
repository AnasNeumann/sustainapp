package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.criteria.ProfilBadgeCriteria;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ProfilBadgeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.BadgeResponse;
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
	@Autowired
	private BadgeServiceDAO badgeService;
	
	/**
	 * get a profile by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!id.isPresent()){
			return super.refuse();
		}
		ProfileEntity profile = profileService.getById(id.get());
		if(null == profile){
			return super.refuse();
		}
		return super.success(new ProfileResponse()
				.setProfile(profile)
				.setCourses(getAllProfileCourses(id.get()))
				.setBadges(getAllProfileBadge(id.get())));
	}
	
	/**
	 * update a profile by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String update(HttpServletRequest request){
		UserAccountEntity user = getConnectedUser(request);
		Map<String, String> errors = profileValidator.validate(request);
		if(null == user || (null != errors && errors.size() > 0) || null == user.getProfile()){
			return new HttpRESTfullResponse().setCode(0).setErrors(errors).buildJson(); 
		}
		user.getProfile()
			.setLastName(request.getParameter("lastName"))
			.setFirstName(request.getParameter("firstName"))
			.setBornDate(DateUtils.ionicParse(request.getParameter("bornDate"), user.getProfile().getBornDate()));
		userService.createOrUpdate(user);
		return new HttpRESTfullResponse().setCode(1).buildJson(); 
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
		user.getProfile().setCover(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
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
		user.getProfile().setAvatar(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		profileService.createOrUpdate(user.getProfile());
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Toogle visibility for a profile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/visibility", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String toogleVisibility(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);	
		if(null == user || null == user.getProfile()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		user.getProfile().setVisibility(user.getProfile().getVisibility().equals(0)? 1 : 0);
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
	
	/**
	 * Get all badges linked to the profile
	 * @param id
	 * @return
	 */
	private List<BadgeResponse> getAllProfileBadge(Long id){
		List<BadgeResponse> result = new ArrayList<BadgeResponse>();
		List<ProfilBadgeEntity> listBadge = getService.cascadeGetProfilBadge(new ProfilBadgeCriteria().setProfilId(id));
		for(BadgeEntity badge : badgeService.getAll()){
			boolean on = false;
			for(ProfilBadgeEntity link : listBadge){
				if(link.getBadge().getId().equals(badge.getId())){
					on = true;
				}
			}
			result.add(new BadgeResponse().setBadge(badge).setOn(on));			
		}
		return result;
	}
}
