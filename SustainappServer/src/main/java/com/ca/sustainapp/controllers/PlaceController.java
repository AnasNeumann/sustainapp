package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

import java.util.GregorianCalendar;
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
import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.criteria.PlacePictureCriteria;
import com.ca.sustainapp.criteria.VisitCriteria;
import com.ca.sustainapp.dao.VisitServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.entities.PlacePictureEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.entities.VisitEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.PlaceResponse;
import com.ca.sustainapp.responses.PlacesResponse;
import com.ca.sustainapp.responses.RankCoursResponse;
import com.ca.sustainapp.utils.FilesUtils;
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
	 * Injection de service DAO
	 */
	@Autowired
	private VisitServiceDAO visitService;
	
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
		List<PlaceNoteEntity> notes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(place.getId()));
		return new PlaceResponse()
				.setIsOwner(super.isOnwerPlace(place, user))
				.setPictures(getService.cascadeGetPlacePictures(new PlacePictureCriteria().setPlaceId(idPlace.get())))
				.setNote(super.getCurrentNote(user.getProfile(), place))
				.setPlace(place)
				.setAverage(calculAverageNotes(notes))
				.setNbrNotes(notes.size())
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
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!id.isPresent() || null == user || !updateValidator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setErrors(updateValidator.validate(request)).setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlacePictureEntity picture = new PlacePictureEntity()
				.setName(request.getParameter("name"))
				.setPlaceId(place.getId())
				.setAbout(request.getParameter("about"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		Long idPicture = pictureService.createOrUpdate(picture);
		return new IdResponse().setId(idPicture).setCode(1).buildJson();
	}

	/**
	 * Del a picture
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/del", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delPicture(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("picture"));
		if(!id.isPresent() || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlacePictureEntity picture = pictureService.getById(id.get());
		if(null == picture){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(picture.getPlaceId());
		if(null == place || !super.isOnwerPlace(place, user)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		deleteService.cascadeDelete(picture);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Note a place
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/place/note", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String note(HttpServletRequest request) {
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		Optional<Integer> score = StringsUtils.parseIntegerQuietly(request.getParameter("score"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!idPlace.isPresent() || !score.isPresent() || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == place){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlaceNoteEntity currentNote;
		List<PlaceNoteEntity> myNotes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(idPlace.get()).setProfilId(user.getProfile().getId()));
		if(myNotes.size() > 0){
			currentNote = myNotes.get(0).setScore(score.get());
		}else {
			currentNote = new PlaceNoteEntity()
					.setPlaceId(idPlace.get())
					.setProfilId(user.getProfile().getId())
					.setScore(score.get())
					.setTimestamps(GregorianCalendar.getInstance());
		}
		noteService.createOrUpdate(currentNote);
		List<PlaceNoteEntity> allNotes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(idPlace.get()));
		Integer total = allNotes.size();
		return new RankCoursResponse().setTotal(total).setAverage(calculAverageNotes(allNotes)).setCode(1).buildJson();
	}

	/**
	 * visite a place
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/place/visit", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String visit(HttpServletRequest request) {
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		Optional<Float> lng = StringsUtils.parseFloatQuiclky(request.getParameter("lng"));
		Optional<Float> lat = StringsUtils.parseFloatQuiclky(request.getParameter("lat"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!idPlace.isPresent() || !lng.isPresent() | !lat.isPresent() || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == place){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		if(Math.abs(lng.get()-place.getLongitude()) > 0.2 || Math.abs(lat.get()-place.getLatitude()) >0.2){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<VisitEntity> currentVisit = getService.cascadeGetVisit(new VisitCriteria().setPlaceId(idPlace.get()).setProfilId(user.getProfile().getId()));
		if(null == currentVisit || currentVisit.size() == 0){
			VisitEntity visit = new VisitEntity()
					.setPlaceId(idPlace.get())
					.setProfilId(user.getProfile().getId())
					.setTimestamps(GregorianCalendar.getInstance());
			visitService.createOrUpdate(visit);
			badgeService.walker(user.getProfile());
		}
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}

}