package com.ca.sustainapp.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.comparators.EntityComparator;
import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TopicServiceDAO;
import com.ca.sustainapp.dao.TopicValidationServiceDAO;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.pojo.SearchResult;

/**
 * Service pour la récupération des liaison sans utiliser hibernate
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 21/03/2107
 * @verion 1.0
 */
@Service("getService")
public class CascadeGetService {

	/**
	 * les services
	 */
	@Autowired
	private ParticipationServiceDAO participationService;
	@Autowired
	private TeamRoleServiceDAO teamRoleService;
	@Autowired
	private CourseServiceDAO courseService;
	@Autowired
	private TopicServiceDAO topicService;
	@Autowired
	private TopicValidationServiceDAO validationService;
	
	/**
	 * Comparator
	 */
	@Autowired
	private EntityComparator compartor;
	
	/**
	 * Cascade get for participation
	 * @param criteria
	 * @return
	 */
	public List<ParticipationEntity> cascadeGetParticipations(ParticipationCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		Long pagination = 100L;
		Long maxResults = pagination;
		SearchResult<ParticipationEntity> result = null;
		List<ParticipationEntity> finalResult = new ArrayList<ParticipationEntity>();
		do {
			result = participationService.searchByCriteres(criteria, startIndex, maxResults);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += pagination;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for TeamRole
	 * @param criteria
	 * @return
	 */
	public List<TeamRoleEntity> cascadeGetTeamRole(TeamRoleCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		Long pagination = 100L;
		Long maxResults = pagination;
		SearchResult<TeamRoleEntity> result = null;
		List<TeamRoleEntity> finalResult = new ArrayList<TeamRoleEntity>();
		do {
			result = teamRoleService.searchByCriteres(criteria, startIndex, maxResults);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += pagination;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for courses
	 * @param criteria
	 * @return
	 */
	public List<CourseEntity> cascadeGetCourses(CourseCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		Long pagination = 100L;
		Long maxResults = pagination;
		SearchResult<CourseEntity> result = null;
		List<CourseEntity> finalResult = new ArrayList<CourseEntity>();
		do {
			result = courseService.searchByCriteres(criteria, startIndex, maxResults);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += pagination;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for topics
	 * @param criteria
	 * @return
	 */
	public List<TopicEntity> cascadeGetTopic(TopicCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		Long pagination = 100L;
		Long maxResults = pagination;
		SearchResult<TopicEntity> result = null;
		List<TopicEntity> finalResult = new ArrayList<TopicEntity>();
		do {
			result = topicService.searchByCriteres(criteria, startIndex, maxResults);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += pagination;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for validation
	 * @param criteria
	 * @return
	 */
	public List<TopicValidationEntity> cascadeGetValidation(TopicValidationCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		Long pagination = 100L;
		Long maxResults = pagination;
		SearchResult<TopicValidationEntity> result = null;
		List<TopicValidationEntity> finalResult = new ArrayList<TopicValidationEntity>();
		do {
			result = validationService.searchByCriteres(criteria, startIndex, maxResults);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += pagination;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
}
