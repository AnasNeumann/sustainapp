package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.validators.AnswerValidator;

/**
 * Restfull controller for answers management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class AnswerController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	AnswerValidator validator;
	
	/**
	 * create a new answer
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a answer by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}	

	/**
	 * drag & drop a answer
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String drop(HttpServletRequest request) {
		return null;
	}
}