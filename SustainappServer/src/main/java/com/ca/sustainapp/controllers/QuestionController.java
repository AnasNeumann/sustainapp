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
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.QuestionsResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.QuestionValidator;

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
	 * Injection of dependencies
	 */
	@Autowired
	private QuestionValidator validator;
	
	/**
	 * get all a questions of a topic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		Optional<Long> topicId = StringsUtils.parseLongQuickly(request.getParameter("topic"));
		Optional<Long> userId = StringsUtils.parseLongQuickly(request.getParameter("id"));
		if(!topicId.isPresent() || !userId.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		TopicEntity topic = topicService.getById(topicId.get());
		UserAccountEntity user = userService.getById(userId.get());
		if(null == topic || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		} 
		return new QuestionsResponse()
				.setQuestions(getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(topicId.get())))
				.setCourseId(topic.getCurseId())
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * create a new question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		TopicEntity topic = super.getTopicIfOwner(request);
		if(null == topic || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		List<QuestionEntity> allQuestions = getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(topic.getId()));
		Integer numero = (null != allQuestions)? allQuestions.size() : 0;
		QuestionEntity question = new QuestionEntity()
				.setMessage(request.getParameter("message"))
				.setNumero(numero)
				.setType(StringsUtils.parseIntegerQuietly(request.getParameter("type")).get())
				.setTopicId(topic.getId())
				.setTimestamps(GregorianCalendar.getInstance());
		if(!isEmpty(request.getParameter("file"))){
			question.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		}
		return new IdResponse().setId(questionService.createOrUpdate(question)).setCode(1).buildJson();
	}
	
	/**
	 * delete a question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String deleteById(HttpServletRequest request) {
		QuestionEntity question = super.getQuestionIfOwner(request);
		if(null == question){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<QuestionEntity> questions = getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(question.getTopicId()));
		for(QuestionEntity q : questions){
			if(q.getNumero() > question.getNumero()){
				questionService.createOrUpdate(q.setNumero(q.getNumero()-1));
			}
		}
		deleteService.cascadeDelete(question);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * drop a question
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/question/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String drop(HttpServletRequest request) {
		QuestionEntity question = super.getQuestionIfOwner(request);
		Optional<Integer> toIndex = StringsUtils.parseIntegerQuietly(request.getParameter("position"));
		if(null == question || !toIndex.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<QuestionEntity> questions = getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(question.getTopicId()));
		if(toIndex.get() > question.getNumero()){
			avancer(question, questions, toIndex.get());
		}else if(toIndex.get() < question.getNumero()){
			reculer(question, questions, toIndex.get());
		}
		return new HttpRESTfullResponse().setCode(1).buildJson();
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
	 * Avancer une question dans la liste
	 * @param topic
	 * @param topics
	 * @param arrivee
	 */
	/**
	 * 
	 * @param question
	 * @param questions
	 * @param arrivee
	 */
	private void avancer(QuestionEntity question, List<QuestionEntity> questions, Integer arrivee){
		for(QuestionEntity q : questions){
			if(q.getNumero() <= arrivee && q.getNumero() > question.getNumero()){
				questionService.createOrUpdate(q.setNumero(q.getNumero()-1));
			}
		}
		questionService.createOrUpdate(question.setNumero(arrivee));
	}
	
	/**
	 * reculer une question dans la liste
	 * @param question
	 * @param questions
	 * @param arrivee
	 */
	private void reculer(QuestionEntity question, List<QuestionEntity> questions, Integer arrivee){
		for(QuestionEntity q : questions){
			if(q.getNumero() >= arrivee && q.getNumero() < question.getNumero()){
				questionService.createOrUpdate(q.setNumero(q.getNumero()+1));
			}
		}
		questionService.createOrUpdate(question.setNumero(arrivee));
	}
	
}
