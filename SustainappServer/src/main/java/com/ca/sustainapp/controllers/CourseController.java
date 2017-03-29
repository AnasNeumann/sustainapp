package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for courses management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController 
public class CourseController extends GenericCourseController {

	/**
	 * get all courses
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		return null;
	}

	/**
	 * get a courses by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getById(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * create a new course
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update a course by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * update a course picture 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/picture", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String picture(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a course by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * toogle open or close
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/open", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String toogleOpen(HttpServletRequest request) {
		return null;
	}

}