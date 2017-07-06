package com.ca.sustainapp.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.ChallengeCriteria;
import com.ca.sustainapp.criteria.ChallengeVoteCriteria;
import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.criteria.NotificationCriteria;
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.criteria.ParticipationCriteria;
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
import com.ca.sustainapp.dao.TeamServiceDAO;
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
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.entities.ResearchEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.entities.VisitEntity;
import com.ca.sustainapp.repositories.AnswerCategoryRepository;
import com.ca.sustainapp.repositories.AnswerRepository;
import com.ca.sustainapp.repositories.ChallengeRepository;
import com.ca.sustainapp.repositories.ChallengeVoteRepository;
import com.ca.sustainapp.repositories.CityRepository;
import com.ca.sustainapp.repositories.CourseRepository;
import com.ca.sustainapp.repositories.NotificationRepository;
import com.ca.sustainapp.repositories.PartRepository;
import com.ca.sustainapp.repositories.ParticipationRepository;
import com.ca.sustainapp.repositories.PlaceNoteRepository;
import com.ca.sustainapp.repositories.PlacePictureRepository;
import com.ca.sustainapp.repositories.PlaceRepository;
import com.ca.sustainapp.repositories.ProfilBadgeRepository;
import com.ca.sustainapp.repositories.ProfileRepository;
import com.ca.sustainapp.repositories.QuestionRepository;
import com.ca.sustainapp.repositories.RankCourseRepository;
import com.ca.sustainapp.repositories.ReportRepository;
import com.ca.sustainapp.repositories.ResearchRepository;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.repositories.TeamRoleRepository;
import com.ca.sustainapp.repositories.TopicRepository;
import com.ca.sustainapp.repositories.TopicValidationRepository;
import com.ca.sustainapp.repositories.UserAccountRepository;
import com.ca.sustainapp.repositories.VisitRepository;

/**
 * Service pour la suppression en cascade
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2017
 * @verion 1.0
 */
@Service("deleteBusinessService")
public class CascadeDeleteService {
	
	/**
	 * les services business
	 */
	@Autowired
	CascadeGetService getService;
	
	/**
	 * Les services DAO
	 */
	@Autowired
	private TeamServiceDAO teamService;

	/**
	 * Les repositories
	 */
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamRoleRepository roleRepository;
	@Autowired
	private ChallengeRepository challengeRepository;
	@Autowired
	private ChallengeVoteRepository voteRepository;
	@Autowired
	private ParticipationRepository participationRepository;
	@Autowired
	private CourseRepository coursRepository;
	@Autowired
	private RankCourseRepository rankRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private PartRepository partRepository;
	@Autowired
	private TopicValidationRepository validationRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private AnswerCategoryRepository answerCategoryRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private PlacePictureRepository placePictureRepository;
	@Autowired
	private PlaceNoteRepository placeNoteRepository;
	@Autowired
	private VisitRepository visitRespositiry;
	@Autowired
	private ProfileRepository profileRespository;
	@Autowired
	private UserAccountRepository userRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ProfilBadgeRepository profilBadgeRepository;
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private ResearchRepository researchRepository;

	/**
	 * cascade delete a user
	 * @param user
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(UserAccountEntity user){
		List<CityEntity> cities = getService.cascadeGet(new CityCriteria().setUserId(user.getId()));
		if(user.getType() == 1 && cities.size() > 0){
			cascadeDelete(cities.get(0));
		}
		cascadeDelete(user.getProfile());
		userRepository.delete(user);
	}

	/**
	 * cascade delete a profile
	 * @param profile
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ProfileEntity profile){
		profileRespository.delete(profile);
		for(NotificationEntity notification : getService.cascadeGet(new NotificationCriteria().setProfilId(profile.getId()))){
			cascadeDelete(notification);
		}
		for(ProfilBadgeEntity badge : getService.cascadeGet(new ProfilBadgeCriteria().setProfilId(profile.getId()))){
			cascadeDelete(badge);
		}
		for(ChallengeVoteEntity vote : getService.cascadeGet(new ChallengeVoteCriteria().setProfilId(profile.getId()))){
			cascadeDelete(vote);
		}
		for(ParticipationEntity participation : getService.cascadeGet(new ParticipationCriteria().setTargetId(profile.getId()).setTargetType(SustainappConstantes.TARGET_PROFILE))){
			cascadeDelete(participation);
		}
		for(ChallengeEntity challenge : getService.cascadeGet(new ChallengeCriteria().setCreatorId(profile.getId()))){
			cascadeDelete(challenge);
		}
		for(ReportEntity report : getService.cascadeGet(new ReportCriteria().setProfilId(profile.getId()))){
			cascadeDelete(report);
		}
		for(ResearchEntity research : getService.cascadeGet(new ResearchCriteria().setProfilId(profile.getId()))){
			cascadeDelete(research);
		}
		for(RankCourseEntity rank : getService.cascadeGet(new RankCourseCriteria().setProfilId(profile.getId()))){
			cascadeDelete(rank);
		}
		for(TopicValidationEntity validation : getService.cascadeGet(new TopicValidationCriteria().setProfilId(profile.getId()))){
			cascadeDelete(validation);
		}
		for(TeamRoleEntity role : getService.cascadeGet(new TeamRoleCriteria().setProfilId(profile.getId()))){
			if(role.getRole().equals(SustainappConstantes.TEAMROLE_ADMIN)){
				cascadeDelete(teamService.getById(role.getTeamId()));
			}else{
				cascadeDelete(role);
			}
		}
		for(CourseEntity course : getService.cascadeGet(new CourseCriteria().setCreatorId(profile.getId()))){
			cascadeDelete(course);
		}
	}

	/**
	 * cascade delete a city
	 * @param city
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(CityEntity city){
		for(PlaceEntity place : city.getPlaces()){
			cascadeDelete(place);
		}
		cityRepository.delete(city);
	}

	/**
	 * cascade delete a cours
	 * @param cours
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(CourseEntity cours){
		for(RankCourseEntity rank : cours.getListRank()){
			cascadeDelete(rank);
		}
		for(TopicEntity topic : getService.cascadeGet(new TopicCriteria().setCurseId(cours.getId()))){
			cascadeDelete(topic);
		}
		coursRepository.delete(cours);
	}

	/**
	 * cascade delete a rank
	 * @param rank
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(RankCourseEntity rank){
		rankRepository.delete(rank);
	}

	/**
	 * cascade delete a topic
	 * @param topic
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TopicEntity topic){
		for(TopicValidationEntity validation : getService.cascadeGet(new TopicValidationCriteria().setTopicId(topic.getId()))){
			cascadeDelete(validation);
		}
		for(PartEntity part : getService.cascadeGet(new PartCriteria().setTopicId(topic.getId()))){
			cascadeDelete(part);
		}
		for(QuestionEntity question : getService.cascadeGet(new QuestionCriteria().setTopicId(topic.getId()))){
			cascadeDelete(question);
		}
		topicRepository.delete(topic);
	}

	/**
	 * cascade delete a part
	 * @param part
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(PartEntity part){
		partRepository.delete(part);
	}

	/**
	 * cascade delete a validation
	 * @param validation
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TopicValidationEntity validation){
		validationRepository.delete(validation);
	}

	/**
	 * cascade delete a challenge
	 * @param challenge
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ChallengeEntity challenge){
		for(ParticipationEntity participation : getService.cascadeGet(new ParticipationCriteria().setChallengeId(challenge.getId()))){
			cascadeDelete(participation);
		}
		challengeRepository.delete(challenge.getId());
	}

	/**
	 * cascade delete a team
	 * @param team
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamEntity team){
		for(ParticipationEntity participation : getService.cascadeGet(new ParticipationCriteria().setTargetId(team.getId()).setTargetType(SustainappConstantes.TARGET_TEAM))){
			cascadeDelete(participation);
		}
		for(TeamRoleEntity role : team.getListRole()){
			cascadeDelete(role);
		}
		teamRepository.delete(team.getId());
	}
	
	/**
	 * cascade delete a question
	 * @param question
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(QuestionEntity question){
		for(AnswerEntity answer : getService.cascadeGet(new AnswerCriteria().setQuestionId(question.getId()))){
			cascadeDelete(answer);
		}
		for(AnswerCategoryEntity category : getService.cascadeGet(new AnswerCategoryCriteria().setQuestionId(question.getId()))){
			cascadeDelete(category);
		}
		questionRepository.delete(question.getId());
	}
	
	/**
	 * cascade delete a participation
	 * @param participation
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ParticipationEntity participation){
		for(ChallengeVoteEntity vote : participation.getVotes()){
			cascadeDelete(vote);
		}
		participationRepository.delete(participation.getId());
	}
	
	/**
	 * cascade delete a answerCategory
	 * @param answerCategory
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(AnswerCategoryEntity answerCategory){
		for(AnswerEntity answer : getService.cascadeGet(new AnswerCriteria().setData(answerCategory.getName()))){
			cascadeDelete(answer);
		}
		answerCategoryRepository.delete(answerCategory.getId());
	}
	
	/**
	 * cascade delete a place
	 * @param place
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(PlaceEntity place){
		for(PlacePictureEntity picture : getService.cascadeGet(new PlacePictureCriteria().setPlaceId(place.getId()))){
			cascadeDelete(picture);
		}
		for(PlaceNoteEntity note : getService.cascadeGet(new PlaceNoteCriteria().setPlaceId(place.getId()))){
			cascadeDelete(note);
		}
		for(VisitEntity visit : getService.cascadeGet(new VisitCriteria().setPlaceId(place.getId()))){
			cascadeDelete(visit);
		}
		placeRepository.delete(place.getId());
	}

	/**
	 * cascade delete a vote
	 * @param vote
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ChallengeVoteEntity vote){
		voteRepository.delete(vote.getId());
	}

	/**
	 * cascade delete a teamRole
	 * @param role
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamRoleEntity role){
		roleRepository.delete(role.getId());
	}
	

	/**
	 * cascade delete a answer
	 * @param answer
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(AnswerEntity answer){
		answerRepository.delete(answer.getId());
	}
	
	/**
	 * cascade delete a place picture
	 * @param picture
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(PlacePictureEntity picture){
		placePictureRepository.delete(picture.getId());
	}
	
	/**
	 * cascade delete a place note
	 * @param note
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(PlaceNoteEntity note){
		placeNoteRepository.delete(note.getId());
	}
	
	/**
	 * cascade delete a visit
	 * @param note
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(VisitEntity visit){
		visitRespositiry.delete(visit.getId());
	}
	
	/**
	 * cascade delete a notification
	 * @param notification
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(NotificationEntity notification){
		notificationRepository.delete(notification.getId());
	}
	
	/**
	 * cascade delete a profile link with badge
	 * @param badge
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ProfilBadgeEntity badge){
		profilBadgeRepository.delete(badge.getId());
	}
	
	/**
	 * cascade delete a report
	 * @param report
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ReportEntity report){
		reportRepository.delete(report.getId());
	}
	
	/**
	 * cascade delete a research
	 * @param research
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(ResearchEntity research){
		researchRepository.delete(research.getId());
	}
}