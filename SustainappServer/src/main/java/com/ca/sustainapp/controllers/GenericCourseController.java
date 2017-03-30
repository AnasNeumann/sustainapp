package com.ca.sustainapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.PartServiceDAO;
import com.ca.sustainapp.dao.TopicServiceDAO;

/**
 * Generic controller for course management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class GenericCourseController extends GenericController{
	
	/**
	 * DAO services
	 */
	@Autowired
	protected CourseServiceDAO courseService;
	@Autowired
	protected TopicServiceDAO topicService;
	@Autowired
	protected PartServiceDAO partService;
	@Autowired
	protected ChallengeTypeServiceDAO challengeTypeService;
}
