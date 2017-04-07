package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for question management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class QuestionController extends GenericCourseController {

	/**
	 * create a new question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get a question by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getById(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get all a questions of a topic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update a question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * modify a question's picture
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/picture", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String picture(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String deleteById(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * drop a question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String drop(HttpServletRequest request) {
		return null;
	}
	
}
