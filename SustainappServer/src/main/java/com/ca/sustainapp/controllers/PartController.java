package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for part management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PartController extends GenericCourseController {

	/**
	 * create a new part
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update a part informations by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a part by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}

}