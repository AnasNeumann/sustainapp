package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.CitiesResponse;
import com.ca.sustainapp.responses.CityResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.LightCityResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.CityValidator;

/**
 * Restfull controller for cities management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class CityController extends GenericController {

	/**
	 * Injection of dependencies
	 */
	@Autowired
	private CityServiceDAO cityService;
	@Autowired
	private CityValidator validator;
	
	/**
	 * get a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		Optional<Long> userId = StringsUtils.parseLongQuickly(request.getParameter("user"));
		if(!id.isPresent() || !userId.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		UserAccountEntity user = userService.getById(userId.get());
		CityEntity city = cityService.getById(id.get());
		if(null == city || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new CityResponse()
				.setCity(city)
				.setOwner(user.getIsAdmin() || city.getUserId().equals(userId.get()))
				.setCode(1)
				.buildJson();
	}

	/**
	 * update cover of a cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/cover", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String cover(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(!id.isPresent() || null == user || isEmpty(request.getParameter("file"))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		CityEntity city = cityService.getById(id.get());
		if(null == city || (!user.getIsAdmin() && !city.getUserId().equals(user.getId()))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		city.setCover(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		cityService.createOrUpdate(city);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}

	/**
	 * update informations of a cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(!id.isPresent() || null == user || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		CityEntity city = cityService.getById(id.get());
		if(null == city || (!user.getIsAdmin() && !city.getUserId().equals(user.getId()))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		city.setName(request.getParameter("name"));
		city.setAbout(request.getParameter("about"));
		city.setPhone(request.getParameter("phone"));
		city.setWebsite(request.getParameter("website"));
		cityService.createOrUpdate(city);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * get all not activated cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		UserAccountEntity user = userService.getById(id.get());
		if(null == user || !user.getIsAdmin()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<LightCityResponse>cities = new ArrayList<LightCityResponse>();
		for(CityEntity city : getService.cascadeGetCities(new CityCriteria().setActif(0))){
			cities.add(new LightCityResponse()
					.setId(city.getId())
					.setName(city.getName())
					.setPhone(city.getPhone())
					.setMail(userService.getById(city.getUserId()).getMail())
			);
		}
		return new CitiesResponse().setCities(cities).setCode(1).buildJson();
	}
	
	/**
	 * validate a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/validate", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String validate(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(null == user || !user.getIsAdmin() || !id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		CityEntity city = cityService.getById(id.get());
		UserAccountEntity owner = userService.getById(city.getUserId());
		cityService.createOrUpdate(city.setActif(1));
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VALIDATION, owner.getProfile().getId(), user.getProfile().getId(), city.getId());
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * delete a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(null == user || !user.getIsAdmin() || !id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		CityEntity city = cityService.getById(id.get());
		UserAccountEntity owner = userService.getById(city.getUserId());
		userService.createOrUpdate(owner.setType(0));
		cityService.delete(id.get());
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_REFUSED, owner.getProfile().getId(), user.getProfile().getId(), owner.getProfile().getId());
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
}