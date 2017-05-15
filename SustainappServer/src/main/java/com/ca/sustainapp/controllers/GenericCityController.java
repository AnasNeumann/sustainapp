package com.ca.sustainapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.PlaceNoteServiceDAO;
import com.ca.sustainapp.dao.PlacePictureServiceDAO;
import com.ca.sustainapp.dao.PlaceServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.UserAccountEntity;

/**
 * Mother class of all controller
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/02/2017
 * @version 1.0
 */
@Controller
@CrossOrigin
public class GenericCityController extends GenericController{

	/**
	 * Injection of dependencies
	 */
	@Autowired
	protected PlaceServiceDAO placeService;
	@Autowired
	protected PlaceNoteServiceDAO noteService;
	@Autowired
	protected PlacePictureServiceDAO pictureService;
	@Autowired
	protected CityServiceDAO cityService;
	
	/**
	 * verifier qu'un user est owner/admin d'une ville
	 * @param placeId
	 * @param user
	 * @return
	 */
	protected boolean isOnwerPlace(PlaceEntity place, UserAccountEntity user){
		CityEntity city = cityService.getById(place.getCityId());
		return isOnwerCity(city, user);
	}
	
	/**
	 * verifier qu'un user est owner/admin d'une ville
	 * @param city
	 * @param user
	 * @return
	 */
	protected boolean isOnwerCity(CityEntity city, UserAccountEntity user){
		if(null == city){
			return false;
		}
		if(user.getIsAdmin()){
			return true;
		}		
		return city.getUserId().equals(user.getId());
	}
	
}
