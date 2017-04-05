package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Collections;
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
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.TopicResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.TopicValidator;

/**
 * Restfull controller for topic management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class TopicController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private TopicValidator validator;
	
	/**
	 * create a new topic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		String title = request.getParameter("title");
		String about = request.getParameter("about");
		CourseEntity cours = getCoursIfOwner(request);
		if(null == cours || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		List<TopicEntity> allTopics = getService.cascadeGetTopic(new TopicCriteria().setCurseId(cours.getId()));
		Integer numero = 0;
		if(null != allTopics){
			numero+=allTopics.size();
		}
		TopicEntity topic = new TopicEntity()
				.setTitle(title)
				.setCurseId(cours.getId())
				.setContent(about)
				.setNumero(numero)
				.setTimestamps(GregorianCalendar.getInstance());
		if(!isEmpty(request.getParameter("file"))){
			topic.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG));
		}
		Long idTopic = topicService.createOrUpdate(topic);
		return new IdResponse().setId(idTopic).setCode(1).buildJson();
	}
	
	/**
	 * get a topic by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getById(HttpServletRequest request) {
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
		List<PartEntity> parts = getService.cascadeGetPart(new PartCriteria().setTopicId(topicId.get()));
		Collections.sort(parts, super.comparatorByNumber);
		return new TopicResponse()
				.setTopic(topic)
				.setIsOwner(super.verifyTopicInformations(topic, user))
				.setParts(parts)
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * delete a topic by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		TopicEntity topic = super.getTopicIfOwner(request);
		if(null == topic){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<TopicEntity> topics = getService.cascadeGetTopic(new TopicCriteria().setCurseId(topic.getCurseId()));
		for(TopicEntity t : topics){
			if(t.getNumero() > topic.getNumero()){
				topicService.createOrUpdate(t.setNumero(t.getNumero()-1));
			}
		}
		deleteService.cascadeDelete(topic);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * update a topic by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("about");
		TopicEntity topic = super.getTopicIfOwner(request);
		if(null == topic || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		topicService.createOrUpdate(topic.setTitle(title).setContent(content));
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * update a topic picture by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic/picture", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String picture(HttpServletRequest request) {
		TopicEntity topic = getTopicIfOwner(request);
		if(null == topic){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		topicService.createOrUpdate(topic.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG)));
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * change topics order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/topic/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String drop(HttpServletRequest request) {
		TopicEntity topic = super.getTopicIfOwner(request);
		Optional<Integer> toIndex = StringsUtils.parseIntegerQuietly(request.getParameter("position"));
		if(null == topic || !toIndex.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<TopicEntity> topics = getService.cascadeGetTopic(new TopicCriteria().setCurseId(topic.getCurseId()));
		if(toIndex.get() > topic.getNumero()){
			avancer(topic, topics, toIndex.get());
		}else if(toIndex.get() < topic.getNumero()){
			reculer(topic, topics, toIndex.get());
		}
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Avancer un chaptire dans la liste
	 * @param topic
	 * @param topics
	 * @param arrivee
	 */
	private void avancer(TopicEntity topic, List<TopicEntity> topics, Integer arrivee){
		for(TopicEntity t : topics){
			if(t.getNumero() <= arrivee && t.getNumero() > topic.getNumero()){
				topicService.createOrUpdate(t.setNumero(t.getNumero()-1));
			}
		}
		topicService.createOrUpdate(topic.setNumero(arrivee));
	}
	
	/**
	 * reculer un chapitre dans la liste
	 * @param topic
	 * @param topics
	 * @param arrivee
	 */
	private void reculer(TopicEntity topic, List<TopicEntity> topics, Integer arrivee){
		for(TopicEntity t : topics){
			if(t.getNumero() >= arrivee && t.getNumero() < topic.getNumero()){
				topicService.createOrUpdate(t.setNumero(t.getNumero()+1));
			}
		}
		topicService.createOrUpdate(topic.setNumero(arrivee));
	}
	
}