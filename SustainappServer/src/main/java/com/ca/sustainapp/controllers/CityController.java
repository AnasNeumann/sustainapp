package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
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
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.CitiesResponse;
import com.ca.sustainapp.responses.CityResponse;
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
    public ResponseEntity<String> get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		Optional<Long> userId = StringsUtils.parseLongQuickly(request.getParameter("user"));
		if(!id.isPresent() || !userId.isPresent()){
			return super.refuse();
		}
		UserAccountEntity user = userService.getById(userId.get());
		CityEntity city = cityService.getById(id.get());
		if(null == city || null == user){
			return super.refuse();
		}
		return super.success(new CityResponse()
				.setCity(city)
				.setOwner(user.getIsAdmin() || city.getUserId().equals(userId.get())));
	}

	/**
	 * update cover of a cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/cover", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> cover(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(!id.isPresent() || null == user || isEmpty(request.getParameter("file"))){
			return super.refuse();
		}
		CityEntity city = cityService.getById(id.get());
		if(null == city || (!user.getIsAdmin() && !city.getUserId().equals(user.getId()))){
			return super.refuse();
		}
		city.setCover(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		cityService.createOrUpdate(city);
		return super.success();
	}

	/**
	 * update informations of a cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(!id.isPresent() || null == user || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
		}
		CityEntity city = cityService.getById(id.get());
		if(null == city || (!user.getIsAdmin() && !city.getUserId().equals(user.getId()))){
			return super.refuse();
		}
		city.setName(request.getParameter("name"));
		city.setAbout(request.getParameter("about"));
		city.setPhone(request.getParameter("phone"));
		city.setWebsite(request.getParameter("website"));
		cityService.createOrUpdate(city);
		return super.success();
	}
	
	/**
	 * get all not activated cities
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getAll(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!id.isPresent()){
			return super.refuse();
		}
		UserAccountEntity user = userService.getById(id.get());
		if(null == user || !user.getIsAdmin()){
			return super.refuse();
		}
		List<LightCityResponse>cities = new ArrayList<LightCityResponse>();
		for(CityEntity city : getService.cascadeGet(new CityCriteria().setActif(0))){
			cities.add(new LightCityResponse()
					.setId(city.getId())
					.setName(city.getName())
					.setPhone(city.getPhone())
					.setMail(userService.getById(city.getUserId()).getMail())
			);
		}
		return super.success(new CitiesResponse().setCities(cities));
	}
	
	/**
	 * validate a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/validate", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> validate(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(null == user || !user.getIsAdmin() || !id.isPresent()){
			return super.refuse();
		}
		CityEntity city = cityService.getById(id.get());
		UserAccountEntity owner = userService.getById(city.getUserId());
		cityService.createOrUpdate(city.setActif(1));
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_VALIDATION, owner.getProfile().getId(), user.getProfile().getId(), city.getId());
		return super.success();
	}
	
	/**
	 * delete a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("city"));
		if(null == user || !user.getIsAdmin() || !id.isPresent()){
			return super.refuse();
		}
		CityEntity city = cityService.getById(id.get());
		UserAccountEntity owner = userService.getById(city.getUserId());
		userService.createOrUpdate(owner.setType(0));
		cityService.delete(id.get());
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_REFUSED, owner.getProfile().getId(), user.getProfile().getId(), owner.getProfile().getId());
		return super.success();
	}
}