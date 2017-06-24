package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
import com.ca.sustainapp.dao.RankCourseServiceDAO;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.CourseResponse;
import com.ca.sustainapp.responses.CoursesResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.LightProfileResponse;
import com.ca.sustainapp.responses.RankCoursResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.CourseUpdateValidator;
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
	@Autowired
	private CourseUpdateValidator updateValidator;
	@Autowired
	private RankCourseServiceDAO rankService;

	
	/**
	 * get all courses
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		if(!startIndex.isPresent()){
			return super.refuse();
		}
		SearchResult<CourseEntity> listResult = courseService.searchByCriteres(null, startIndex.get(), 20L);
		List<CourseEntity> courses = new SustainappList<CourseEntity>();
		for(CourseEntity course : listResult.getResults()){
			if(0 != course.getOpen()) {
				courses.add(course);
			}			
		}
		return super.success(new CoursesResponse().setCours(courses));
	}

	/**
	 * create a new course
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> create(HttpServletRequest request) {
		if(!isConnected(request) || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
		}
		CourseEntity course = new CourseEntity()
				.setAbout(request.getParameter("about"))
				.setLevelMin(StringsUtils.parseIntegerQuietly(request.getParameter("levelMin")).get())
				.setCreatorId(super.getUser(request).getProfile().getId())
				.setOpen(0)
				.setLanguage(!request.getParameter("langue").isEmpty() ? request.getParameter("langue") : "en")
				.setTitle(request.getParameter("title"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setType(challengeTypeService.getById(StringsUtils.parseLongQuickly(request.getParameter("type")).get()));
		if(!isEmpty(request.getParameter("file"))){
			course.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		}
		Long idCours = courseService.createOrUpdate(course);
		return super.success(new IdResponse().setId(idCours));
	}

	/**
	 * get a courses by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getById(HttpServletRequest request) {
		Optional<Long> coursId = StringsUtils.parseLongQuickly(request.getParameter("cours"));
		Optional<Long> userId = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!coursId.isPresent() || !userId.isPresent()){
			return super.refuse();
		}
		CourseEntity cours = courseService.getById(coursId.get());
		UserAccountEntity user = userService.getById(userId.get());
		if(null == cours || null == user){
			return super.refuse();
		}
		boolean isOwner = cours.getCreatorId().equals(user.getProfile().getId()) || user.getIsAdmin();
		if(!isOwner){
			courseService.createOrUpdate(cours.setViews(cours.getViews()+1));
		}
		return super.success(new CourseResponse()
				.setCours(cours)
				.setOwner(new LightProfileResponse(profileService.getById(cours.getCreatorId())))
				.setIsOwner(isOwner)
				.setAverageRank(calculateAverageRank(cours))
				.setRank(getOwnRank(user.getProfile().getId(), cours))
				.setTopics(loadAllTopics(cours, user.getProfile().getId()))
				.setHasLevel((user.getProfile().getLevel() >= cours.getLevelMin()) || user.getIsAdmin()));
	}
	
	/**
	 * update a course by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		String title = request.getParameter("title");
		String about = request.getParameter("about");
		CourseEntity cours = getCoursIfOwner(request);
		if(null == cours || !updateValidator.validate(request).isEmpty()){
			return super.refuse(updateValidator.validate(request));
		}
		courseService.createOrUpdate(cours.setTitle(title).setAbout(about));
		return super.success();
	}
	
	/**
	 * update a course level by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/level", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> level(HttpServletRequest request) {
		CourseEntity cours = getCoursIfOwner(request);
		Optional<Integer> level = StringsUtils.parseIntegerQuietly(request.getParameter("level"));
		if(null == cours || !level.isPresent()){
			return super.refuse();
		}
		courseService.createOrUpdate(cours.setLevelMin(level.get()));
		return super.success();
	}
	
	
	/**
	 * update a course picture 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/picture", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> picture(HttpServletRequest request) {
		CourseEntity cours = getCoursIfOwner(request);
		if(null == cours){
			return super.refuse();
		}
		courseService.createOrUpdate(cours.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG)));
		return super.success();
	}
	
	/**
	 * delete a course by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		CourseEntity cours = getCoursIfOwner(request);
		if(null == cours){
			return super.refuse();
		}
		deleteService.cascadeDelete(cours);
		return super.success();
	}
	
	/**
	 * toogle open or close
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/open", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> toogleOpen(HttpServletRequest request) {
		CourseEntity cours = getCoursIfOwner(request);
		if(null == cours){
			return super.refuse();
		}
		courseService.createOrUpdate(cours.setOpen(cours.getOpen().equals(1) ? 0 : 1));
		return super.success();
	}
	
	/**
	 * note a courses
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/course/note", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> note(HttpServletRequest request) {
		Optional<Long> idCours = StringsUtils.parseLongQuickly(request.getParameter("cours"));
		Optional<Integer> score = StringsUtils.parseIntegerQuietly(request.getParameter("score"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!idCours.isPresent() || !score.isPresent() || null == user){
			return super.refuse();
		}
		CourseEntity cours = courseService.getById(idCours.get());
		if(null == cours){
			return super.refuse();
		}
		Integer total = cours.getListRank().size();
		RankCourseEntity rank = super.getOwnRank(user.getProfile().getId(), cours);
		if(null == rank){
			total++;
			rank = new RankCourseEntity()
					.setCourseId(idCours.get())
					.setProfilId(user.getProfile().getId())
					.setScore(score.get())
					.setTimestamps(GregorianCalendar.getInstance());
		}
		notificationService.create(SustainappConstantes.NOTIFICATION_MESSAGE_RANK, cours.getCreatorId(), user.getProfile().getId(), cours.getId());
		rank.setScore(score.get());
		rankService.createOrUpdate(rank);
		badgeService.teacher(profileService.getById(cours.getCreatorId()));
		return super.success(new RankCoursResponse().setTotal(total).setAverage(super.calculateAverageRank(courseService.getById(idCours.get()))));
	}

}