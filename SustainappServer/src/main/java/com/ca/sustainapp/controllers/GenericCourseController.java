package com.ca.sustainapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.PartServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TopicServiceDAO;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.LightTopicResponse;

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
	protected ProfileServiceDAO profileService;
	@Autowired
	protected CourseServiceDAO courseService;
	@Autowired
	protected TopicServiceDAO topicService;
	@Autowired
	protected PartServiceDAO partService;
	@Autowired
	protected ChallengeTypeServiceDAO challengeTypeService;
	
	/**
	 * Calculate average rank of a course
	 * @param cours
	 * @return
	 */
	protected Float calculateAverageRank(CourseEntity course){
		int totalResult = 0;
		int somme = 0;
		for(RankCourseEntity rank : course.getListRank()){
			somme += rank.getScore();
			totalResult++;
		}
		if(totalResult > 0){
			return new Float(somme/totalResult);
		}
		return 0F;
	}
	
	/**
	 * Search rank by profile
	 * @param idProfil
	 * @return
	 */
	protected RankCourseEntity getOwnRank(Long idProfil, CourseEntity course){
		for(RankCourseEntity rank : course.getListRank()){
			if(rank.getProfilId().equals(idProfil)){
				return rank;
			}
		}
		return null;
	}
	
	/**
	 * Search all topics for a course
	 * @param course
	 * @return
	 */
	protected List<LightTopicResponse> loadAllTopics(CourseEntity course, Long profileId){
		List<LightTopicResponse> result = new SustainappList<LightTopicResponse>();
		List<TopicEntity> topics = getService.cascadeGetTopic(new TopicCriteria().setCurseId(course.getId()));
		for(TopicEntity topic : topics){
			result.add(new LightTopicResponse().setTopic(topic).setDone(alreadyTopicValided(topic.getId(), profileId)));
		}
		return result;
	}
	
	
	/**
	 * Verify if a profile valided a topic
	 * @param topicId
	 * @param profilId
	 * @return
	 */
	protected boolean alreadyTopicValided(Long topicId, Long profileId){
		for(TopicValidationEntity validation : getAllTopicValidation(topicId)){
			if(validation.getProfilId().equals(profileId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get all validation of a 
	 * @param topicId
	 * @return
	 */
	protected List<TopicValidationEntity> getAllTopicValidation(Long topicId){
		return getService.cascadeGetValidation(new TopicValidationCriteria().setTopicId(topicId));
	}
}
