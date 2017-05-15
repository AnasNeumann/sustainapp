package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.PlaceNoteServiceDAO;
import com.ca.sustainapp.dao.PlacePictureServiceDAO;
import com.ca.sustainapp.dao.PlaceServiceDAO;

/**
 * Restfull controller for places management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PlaceController extends GenericController {
	
	/**
	 * Injection of dependencies
	 */
	@Autowired
	private PlaceServiceDAO placeService;
	@Autowired
	private PlaceNoteServiceDAO noteService;
	@Autowired
	private PlacePictureServiceDAO pictureService;

	/**
	 * get a place by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		return null;
	}

	/**
	 * create a new place
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}

	/**
	 * update a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}

	/**
	 * delete a place by Id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}

	/**
	 * Add a picture to a place
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/place/picture/add", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String addPicture(HttpServletRequest request) {
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