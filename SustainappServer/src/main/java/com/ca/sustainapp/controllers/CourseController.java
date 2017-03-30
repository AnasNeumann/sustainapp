package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.CoursesResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.CourseValidator;

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
	 * Injection de dépendances
	 */
	@Autowired
	private CourseValidator validator;
	
	/**
	 * get all courses
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		if(!startIndex.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		SearchResult<CourseEntity> listResult = courseService.searchByCriteres(null, startIndex.get(), 20L);
		List<CourseEntity> courses = new SustainappList<CourseEntity>();
		for(CourseEntity course : listResult.getResults()){
			if(0 != course.getOpen()) {
				courses.add(course);
			}			
		}
		return new CoursesResponse().setCours(courses).setCode(1).buildJson();
	}

	/**
	 * create a new course
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		CourseEntity course = new CourseEntity()
				.setAbout(request.getParameter("about"))
				.setLevelMin(StringsUtils.parseIntegerQuietly(request.getParameter("levelMin")).get())
				.setCreatorId(super.getConnectedUser(request).getProfile().getId())
				.setOpen(0)
				.setTitle(request.getParameter("title"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setType(challengeTypeService.getById(StringsUtils.parseLongQuickly(request.getParameter("type")).get()));
		if(!isEmpty(request.getParameter("file"))){
			course.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		}
		Long idCours = courseService.createOrUpdate(course);
		return new IdResponse().setId(idCours).setCode(1).buildJson();
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