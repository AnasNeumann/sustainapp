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
import com.ca.sustainapp.criteria.ChallengeCriteria;
import com.ca.sustainapp.criteria.ChallengeVoteCriteria;
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
import com.ca.sustainapp.criteria.RankCourseCriteria;
import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.criteria.ResearchCriteria;
import com.ca.sustainapp.criteria.TeamRoleCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.criteria.VisitCriteria;
import com.ca.sustainapp.dao.AnswerCategoryServiceDAO;
import com.ca.sustainapp.dao.AnswerServiceDAO;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ChallengeVoteServiceDAO;
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
import com.ca.sustainapp.dao.RankCourseServiceDAO;
import com.ca.sustainapp.dao.ReportServiceDAO;
import com.ca.sustainapp.dao.ResearchServiceDAO;
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TopicServiceDAO;
import com.ca.sustainapp.dao.TopicValidationServiceDAO;
import com.ca.sustainapp.dao.VisitServiceDAO;
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
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
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.entities.ResearchEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.entities.VisitEntity;
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
	@Autowired
	private VisitServiceDAO visitService;
	@Autowired
	private ChallengeVoteServiceDAO challengeVoteService;
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private ResearchServiceDAO researchService;
	@Autowired
	private RankCourseServiceDAO rankService;
	
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
	 * Cascade get for rank course
	 * @param research
	 * @return
	 */
	public List<RankCourseEntity> cascadeGet(RankCourseCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<RankCourseEntity> result = null;
		List<RankCourseEntity> finalResult = new ArrayList<RankCourseEntity>();
		do {
			result = rankService.searchByCriteres(criteria, startIndex, PAGINATION);
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
	 * Cascade get for research
	 * @param research
	 * @return
	 */
	public List<ResearchEntity> cascadeGet(ResearchCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ResearchEntity> result = null;
		List<ResearchEntity> finalResult = new ArrayList<ResearchEntity>();
		do {
			result = researchService.searchByCriteres(criteria, startIndex, PAGINATION);
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
	 * Cascade get for challenge
	 * @param criteria
	 * @return
	 */
	public List<ChallengeEntity> cascadeGet(ChallengeCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ChallengeEntity> result = null;
		List<ChallengeEntity> finalResult = new ArrayList<ChallengeEntity>();
		do {
			result = challengeService.searchByCriteres(criteria, startIndex, PAGINATION);
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
	 * Cascade get for vote
	 * @param criteria
	 * @return
	 */
	public List<ChallengeVoteEntity> cascadeGet(ChallengeVoteCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<ChallengeVoteEntity> result = null;
		List<ChallengeVoteEntity> finalResult = new ArrayList<ChallengeVoteEntity>();
		do {
			result = challengeVoteService.searchByCriteres(criteria, startIndex, PAGINATION);
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
	 * Cascade get for visit
	 * @param criteria
	 * @return
	 */
	public List<VisitEntity> cascadeGet(VisitCriteria criteria){
		Long startIndex = 0L;
		Long incrementsEntries = 0L;
		Long totalResults = null;
		SearchResult<VisitEntity> result = null;
		List<VisitEntity> finalResult = new ArrayList<VisitEntity>();
		do {
			result = visitService.searchByCriteres(criteria, startIndex, PAGINATION);
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
	 * Cascade get for place notes
	 * @param criteria
	 * @return
	 */
	public List<PlaceNoteEntity> cascadeGet(PlaceNoteCriteria criteria){
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
	public List<PlacePictureEntity> cascadeGet(PlacePictureCriteria criteria){
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
	public List<PlaceEntity> cascadeGet(PlaceCriteria criteria){
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
	public List<CityEntity> cascadeGet(CityCriteria criteria){
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
	public List<NotificationEntity> cascadeGet(NotificationCriteria criteria){
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
	public List<ProfilBadgeEntity> cascadeGet(ProfilBadgeCriteria criteria){
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
	public List<ReportEntity> cascadeGet(ReportCriteria criteria){
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
	public List<PartEntity> cascadeGet(PartCriteria criteria){
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
	public List<ParticipationEntity> cascadeGet(ParticipationCriteria criteria){
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
	public List<TeamRoleEntity> cascadeGet(TeamRoleCriteria criteria){
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
	public List<CourseEntity> cascadeGet(CourseCriteria criteria){
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
	public List<TopicEntity> cascadeGet(TopicCriteria criteria){
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
	public List<TopicValidationEntity> cascadeGet(TopicValidationCriteria criteria){
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
	public List<QuestionEntity> cascadeGet(QuestionCriteria criteria){
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
	public List<AnswerEntity> cascadeGet(AnswerCriteria criteria){
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
	public List<AnswerCategoryEntity> cascadeGet(AnswerCategoryCriteria criteria){
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