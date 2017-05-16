package com.ca.sustainapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.PlaceNoteServiceDAO;
import com.ca.sustainapp.dao.PlacePictureServiceDAO;
import com.ca.sustainapp.dao.PlaceServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.entities.ProfileEntity;
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

	/***
	 * Get the current note of a profil for a place
	 * @param profil
	 * @param place
	 * @return
	 */
	protected PlaceNoteEntity getCurrentNote(ProfileEntity profil, PlaceEntity place){
		List<PlaceNoteEntity> notes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(place.getId()).setProfilId(profil.getId()));
		if(null != notes && notes.size() >0){
			return notes.get(0);
		}
		return null;
	}
	
	/**
	 * Compute average note for a place
	 * @param place
	 * @return
	 */
	protected Float calculAverageNotes(PlaceEntity place){
		Float result = 0F;
		List<PlaceNoteEntity> notes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(place.getId()));
		for(PlaceNoteEntity note : notes){
			result += note.getScore();
		}
		if(notes.size() >0){
			result = new Float(result/notes.size());
		}
		return result;
	}
}
