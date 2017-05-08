package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for answers management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class AdministrationController extends GenericCourseController {

	/**
	 * get data for courses
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/courses", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String courses(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get data for research
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/research", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String research(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get data for challenges
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/challenges", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String challenges(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * get data for profiles
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/profiles", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String profiles(HttpServletRequest request) {
		return null;
	}

}