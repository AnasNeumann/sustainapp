package com.ca.sustainapp.controllers;

import java.util.GregorianCalendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.PlacePictureCriteria;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.PlaceResponse;
import com.ca.sustainapp.responses.PlacesResponse;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.PlaceUpdateValidator;
import com.ca.sustainapp.validators.PlaceValidator;

/**
 * Restfull controller for places management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PlaceController extends GenericCityController {
	
	/**
	 * Validator
	 */
	@Autowired
	private PlaceValidator validator;
	@Autowired
	private PlaceUpdateValidator updateValidator;

	/**
	 * create a new place
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request){
		CityEntity city = cityService.getById(StringsUtils.parseLongQuickly(request.getParameter("city")).get());
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !super.isOnwerCity(city, user) || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		PlaceEntity place = new PlaceEntity()
				.setName(request.getParameter("name"))
				.setAbout(request.getParameter("about"))
				.setAddress(request.getParameter("address"))
				.setCityId(city.getId())
				.setLatitude(StringsUtils.parseFloatQuiclky(request.getParameter("latitude")).get())
				.setLongitude(StringsUtils.parseFloatQuiclky(request.getParameter("longitude")).get())
				.setTimestamps(GregorianCalendar.getInstance());
		place.setId(placeService.createOrUpdate(place));
		return new IdResponse().setId(place.getId()).setCode(1).buildJson();
	}
	
	/**
	 * Récupération des éco-lieux les plus proches
     * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/near", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getNear(HttpServletRequest request){
		Optional<Float> lng = StringsUtils.parseFloatQuiclky(request.getParameter("lng"));
		Optional<Float> lat = StringsUtils.parseFloatQuiclky(request.getParameter("lat"));
		if(!lat.isPresent() || !lng.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new PlacesResponse().setPlaces(placeService.getNear(lng.get(), lat.get())).setCode(1).buildJson();
	}

	/**
	 * get a place by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request){
		Optional<Long> idUser = StringsUtils.parseLongQuickly(request.getParameter("user"));
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!idUser.isPresent() || !idPlace.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		UserAccountEntity user = userService.getById(idUser.get());
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == user || null == place){
			return new HttpRESTfullResponse().setCode(0).buildJson(); 
		}
		return new PlaceResponse()
				.setIsOwner(super.isOnwerPlace(place, user))
				.setPictures(getService.cascadeGetPlacePictures(new PlacePictureCriteria().setPlaceId(idPlace.get())))
				.setNote(super.getCurrentNote(user.getProfile(), place))
				.setPlace(place)
				.setAverage(calculAverageNotes(place))
				.setCode(1)
				.buildJson();		
	}

	/**
	 * delete a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !id.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		deleteService.cascadeDelete(place);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}

	/**
	 * update a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!id.isPresent() || null == user || !updateValidator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setErrors(updateValidator.validate(request)).setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		place.setName(request.getParameter("name")).setAbout(request.getParameter("about")).setAddress(request.getParameter("address"));
		placeService.createOrUpdate(place);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Add a picture to a place
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/add", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String addPicture(HttpServletRequest request){
		return null;
	}

	/**
	 * Del a picture
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/dell", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delPicture(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * Note a place
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/place/note", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String note(HttpServletRequest request) {
		return null;
	}

}