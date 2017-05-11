package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
import com.ca.sustainapp.responses.CityResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;

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
	CityServiceDAO cityService;
	
	/**
	 * get a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		CityEntity city = cityService.getById(id.get());
		if(null == city){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new CityResponse()
				.setCity(city)
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
		if(null == user || isEmpty(request.getParameter("file"))){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		CityEntity city = getService.cascadeGetCities(new CityCriteria().setUserId(user.getId())).get(0);
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
		return null;
	}
	
	/**
	 * validate a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/validate", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String validate(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a city by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/city/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
}