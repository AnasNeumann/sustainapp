package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

import java.util.GregorianCalendar;
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
    public ResponseEntity<String> create(HttpServletRequest request){
		CityEntity city = cityService.getById(StringsUtils.parseLongQuickly(request.getParameter("city")).get());
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !super.isOnwerCity(city, user) || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
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
		return super.success(new IdResponse().setId(place.getId()));
	}
	
	/**
	 * Récupération des éco-lieux les plus proches
     * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/near", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getNear(HttpServletRequest request){
		Optional<Float> lng = StringsUtils.parseFloatQuiclky(request.getParameter("lng"));
		Optional<Float> lat = StringsUtils.parseFloatQuiclky(request.getParameter("lat"));
		if(!lat.isPresent() || !lng.isPresent()){
			return super.refuse();
		}
		return super.success(new PlacesResponse().setPlaces(placeService.getNear(lng.get(), lat.get())));
	}

	/**
	 * get a place by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> get(HttpServletRequest request){
		Optional<Long> idUser = StringsUtils.parseLongQuickly(request.getParameter("user"));
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!idUser.isPresent() || !idPlace.isPresent()){
			return super.refuse();
		}
		UserAccountEntity user = userService.getById(idUser.get());
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == user || null == place){
			return super.refuse(); 
		}
		List<PlaceNoteEntity> notes = getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(place.getId()));
		return super.success(new PlaceResponse()
				.setIsOwner(super.isOnwerPlace(place, user))
				.setPictures(getService.cascadeGetPlacePictures(new PlacePictureCriteria().setPlaceId(idPlace.get())))
				.setNote(super.getCurrentNote(user.getProfile(), place))
				.setPlace(place)
				.setAverage(calculAverageNotes(notes))
				.setNbrNotes(notes.size()));
	}

	/**
	 * delete a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !id.isPresent()){
			return super.refuse();
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return super.refuse();
		}
		deleteService.cascadeDelete(place);
		return super.success();
	}

	/**
	 * update a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!id.isPresent() || null == user || !updateValidator.validate(request).isEmpty()){
			return super.refuse(updateValidator.validate(request));
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return super.refuse();
		}
		place.setName(request.getParameter("name")).setAbout(request.getParameter("about")).setAddress(request.getParameter("address"));
		placeService.createOrUpdate(place);
		return super.success();
	}
	
	/**
	 * Add a picture to a place
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/add", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> addPicture(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("place"));
		if(!id.isPresent() || null == user || !updateValidator.validate(request).isEmpty()){
			return super.refuse(updateValidator.validate(request));
		}
		PlaceEntity place = placeService.getById(id.get());
		if(null == place || !super.isOnwerPlace(place, user)){
			return super.refuse();
		}
		PlacePictureEntity picture = new PlacePictureEntity()
				.setName(request.getParameter("name"))
				.setPlaceId(place.getId())
				.setAbout(request.getParameter("about"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		Long idPicture = pictureService.createOrUpdate(picture);
		return super.success(new IdResponse().setId(idPicture));
	}

	/**
	 * Del a picture
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/del", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delPicture(HttpServletRequest request) {
		UserAccountEntity user = super.getConnectedUser(request);
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("picture"));
		if(!id.isPresent() || null == user){
			return super.refuse();
		}
		PlacePictureEntity picture = pictureService.getById(id.get());
		if(null == picture){
			return super.refuse();
		}
		PlaceEntity place = placeService.getById(picture.getPlaceId());
		if(null == place || !super.isOnwerPlace(place, user)){
			return super.refuse();
		}
		deleteService.cascadeDelete(picture);
		return super.success();
	}
	
	/**
	 * Note a place
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/place/note", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> note(HttpServletRequest request) {
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		Optional<Integer> score = StringsUtils.parseIntegerQuietly(request.getParameter("score"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!idPlace.isPresent() || !score.isPresent() || null == user){
			return super.refuse();
		}
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == place){
			return super.refuse();
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
		return super.success(new RankCoursResponse().setTotal(total).setAverage(calculAverageNotes(allNotes)));
	}

	/**
	 * visite a place
	 * @param request
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/place/visit", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> visit(HttpServletRequest request) {
		Optional<Long> idPlace = StringsUtils.parseLongQuickly(request.getParameter("place"));
		Optional<Float> lng = StringsUtils.parseFloatQuiclky(request.getParameter("lng"));
		Optional<Float> lat = StringsUtils.parseFloatQuiclky(request.getParameter("lat"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!idPlace.isPresent() || !lng.isPresent() | !lat.isPresent() || null == user){
			return super.refuse();
		}
		PlaceEntity place = placeService.getById(idPlace.get());
		if(null == place){
			return super.refuse();
		}
		if(Math.abs(lng.get()-place.getLongitude()) > 0.2 || Math.abs(lat.get()-place.getLatitude()) >0.2){
			return super.refuse();
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
		return super.success();
	}

}