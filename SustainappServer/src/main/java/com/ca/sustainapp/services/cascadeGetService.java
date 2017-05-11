package com.ca.sustainapp.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.comparators.EntityComparator;
import com.ca.sustainapp.comparators.NumerotableEntityComparator;
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.criteria.NotificationCriteria;
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.criteria.PlaceCriteria;
import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.criteria.PlacePictureCriteria;
import com.ca.sustainapp.criteria.ProfilBadgeCriteria;
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.AnswerCategoryServiceDAO;
import com.ca.sustainapp.dao.AnswerServiceDAO;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.NotificationServiceDAO;
import com.ca.sustainapp.dao.PartServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.PlaceNoteServiceDAO;
import com.ca.sustainapp.dao.PlacePictureServiceDAO;
import com.ca.sustainapp.dao.PlaceServiceDAO;
import com.ca.sustainapp.dao.ProfilBadgeServiceDAO;
import com.ca.sustainapp.dao.QuestionServiceDAO;
import com.ca.sustainapp.dao.ReportServiceDAO;
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TopicServiceDAO;
import com.ca.sustainapp.dao.TopicValidationServiceDAO;
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.NotificationEntity;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.entities.PlacePictureEntity;
import com.ca.sustainapp.entities.ProfilBadgeEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.pojo.SearchResult;

/**
 * Service pour la récupération des liaison sans utiliser hibernate
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 21/03/2017
 * @verion 1.0
 */
@Service("getBusinessService")
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
	@Autowired
	private PartServiceDAO partService;
	@Autowired
	private QuestionServiceDAO questionService;
	@Autowired
	private AnswerServiceDAO answerService;
	@Autowired
	private AnswerCategoryServiceDAO answerCategoryService;
	@Autowired
	private ProfilBadgeServiceDAO profilBadgeService;
	@Autowired
	private ReportServiceDAO reportService;
	@Autowired
	private NotificationServiceDAO notificationService;
	@Autowired
	private CityServiceDAO cityService;
	@Autowired
	private PlaceServiceDAO placeService;
	@Autowired
	private PlacePictureServiceDAO placePictureService;
	@Autowired
	private PlaceNoteServiceDAO placeNoteService;
	
	/**
	 * Comparator
	 */
	@Autowired
	private EntityComparator compartor;
	@Autowired
	private NumerotableEntityComparator numerotableComparator;
	
	/**
	 * Les constantes
	 */
	private Long PAGINATION = 100L;
	
	/**
	 * Cascade get for place notes
	 * @param criteria
	 * @return
	 */
	public List<PlaceNoteEntity> cascadeGetPlaceNotes(PlaceNoteCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<PlaceNoteEntity> result = null;
		List<PlaceNoteEntity> finalResult = new ArrayList<PlaceNoteEntity>();
		do {
			result = placeNoteService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for place pictures
	 * @param criteria
	 * @return
	 */
	public List<PlacePictureEntity> cascadeGetPlacePictures(PlacePictureCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<PlacePictureEntity> result = null;
		List<PlacePictureEntity> finalResult = new ArrayList<PlacePictureEntity>();
		do {
			result = placePictureService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for places
	 * @param criteria
	 * @return
	 */
	public List<PlaceEntity> cascadeGetPlaces(PlaceCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<PlaceEntity> result = null;
		List<PlaceEntity> finalResult = new ArrayList<PlaceEntity>();
		do {
			result = placeService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for cities
	 * @param criteria
	 * @return
	 */
	public List<CityEntity> cascadeGetCities(CityCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<CityEntity> result = null;
		List<CityEntity> finalResult = new ArrayList<CityEntity>();
		do {
			result = cityService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for notifications
	 * @param criteria
	 * @return
	 */
	public List<NotificationEntity> cascadeGetNotifications(NotificationCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<NotificationEntity> result = null;
		List<NotificationEntity> finalResult = new ArrayList<NotificationEntity>();
		do {
			result = notificationService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
		
	/**
	 * Cascade get for profilBadge
	 * @param criteria
	 * @return
	 */
	public List<ProfilBadgeEntity> cascadeGetProfilBadge(ProfilBadgeCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ProfilBadgeEntity> result = null;
		List<ProfilBadgeEntity> finalResult = new ArrayList<ProfilBadgeEntity>();
		do {
			result = profilBadgeService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for report
	 * @param criteria
	 * @return
	 */
	public List<ReportEntity> cascadeGetReport(ReportCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ReportEntity> result = null;
		List<ReportEntity> finalResult = new ArrayList<ReportEntity>();
		do {
			result = reportService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for part
	 * @param criteria
	 * @return
	 */
	public List<PartEntity> cascadeGetPart(PartCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<PartEntity> result = null;
		List<PartEntity> finalResult = new ArrayList<PartEntity>();
		do {
			result = partService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, numerotableComparator);
		return finalResult;
	}
	
	/**
	 * Cascade get for participation
	 * @param criteria
	 * @return
	 */
	public List<ParticipationEntity> cascadeGetParticipations(ParticipationCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ParticipationEntity> result = null;
		List<ParticipationEntity> finalResult = new ArrayList<ParticipationEntity>();
		do {
			result = participationService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
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
		Long maxResults = PAGINATION;
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
			incrementsEntries += PAGINATION;
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
		SearchResult<CourseEntity> result = null;
		List<CourseEntity> finalResult = new ArrayList<CourseEntity>();
		do {
			result = courseService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
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
		SearchResult<TopicEntity> result = null;
		List<TopicEntity> finalResult = new ArrayList<TopicEntity>();
		do {
			result = topicService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, numerotableComparator);
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
		SearchResult<TopicValidationEntity> result = null;
		List<TopicValidationEntity> finalResult = new ArrayList<TopicValidationEntity>();
		do {
			result = validationService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, compartor);
		return finalResult;
	}
	
	/**
	 * Cascade get for question
	 * @param criteria
	 * @return
	 */
	public List<QuestionEntity> cascadeGetQuestion(QuestionCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<QuestionEntity> result = null;
		List<QuestionEntity> finalResult = new ArrayList<QuestionEntity>();
		do {
			result = questionService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, numerotableComparator);
		return finalResult;
	}
	
	/**
	 * Cascade get for answer
	 * @param criteria
	 * @return
	 */
	public List<AnswerEntity> cascadeGetAnswer(AnswerCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<AnswerEntity> result = null;
		List<AnswerEntity> finalResult = new ArrayList<AnswerEntity>();
		do {
			result = answerService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, numerotableComparator);
		return finalResult;
	}
	
	/**
	 * Cascade get for answerCategory
	 * @param criteria
	 * @return
	 */
	public List<AnswerCategoryEntity> cascadeGetAnswerCateogry(AnswerCategoryCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<AnswerCategoryEntity> result = null;
		List<AnswerCategoryEntity> finalResult = new ArrayList<AnswerCategoryEntity>();
		do {
			result = answerCategoryService.searchByCriteres(criteria, startIndex, PAGINATION);
			if (null == totalResults && null != result) {
				totalResults = result.getTotalResults();
			}
			if (null != result && !result.getResults().isEmpty()) {
				finalResult.addAll(result.getResults());
			}
			startIndex++;
			incrementsEntries += PAGINATION;
		} while (incrementsEntries < totalResults);
		Collections.sort(finalResult, numerotableComparator);
		return finalResult;
	}
	
}
