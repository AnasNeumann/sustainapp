package com.ca.sustainapp.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.criteria.PlacePictureCriteria;
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.criteria.VisitCriteria;
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.entities.PlacePictureEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.entities.VisitEntity;
import com.ca.sustainapp.repositories.AnswerCategoryRepository;
import com.ca.sustainapp.repositories.AnswerRepository;
import com.ca.sustainapp.repositories.ChallengeRepository;
import com.ca.sustainapp.repositories.ChallengeVoteRepository;
import com.ca.sustainapp.repositories.CityRepository;
import com.ca.sustainapp.repositories.CourseRepository;
import com.ca.sustainapp.repositories.PartRepository;
import com.ca.sustainapp.repositories.ParticipationRepository;
import com.ca.sustainapp.repositories.PlaceNoteRepository;
import com.ca.sustainapp.repositories.PlacePictureRepository;
import com.ca.sustainapp.repositories.PlaceRepository;
import com.ca.sustainapp.repositories.QuestionRepository;
import com.ca.sustainapp.repositories.RankCourseRepository;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.repositories.TeamRoleRepository;
import com.ca.sustainapp.repositories.TopicRepository;
import com.ca.sustainapp.repositories.TopicValidationRepository;
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
	 * Les repositories
	 */
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	TeamRoleRepository roleRepository;
	@Autowired
	ChallengeRepository challengeRepository;
	@Autowired
	ChallengeVoteRepository voteRepository;
	@Autowired
	ParticipationRepository participationRepository;
	@Autowired
	CourseRepository coursRepository;
	@Autowired
	RankCourseRepository rankRepository;
	@Autowired
	TopicRepository topicRepository;
	@Autowired
	PartRepository partRepository;
	@Autowired
	TopicValidationRepository validationRepository;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	AnswerCategoryRepository answerCategoryRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	PlacePictureRepository placePictureRepository;
	@Autowired
	PlaceNoteRepository placeNoteRepository;
	@Autowired
	VisitRepository visitRespositiry;
	
	/**
	 * les services
	 */
	@Autowired
	CascadeGetService getService;

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
		for(TopicEntity topic : getService.cascadeGetTopic(new TopicCriteria().setCurseId(cours.getId()))){
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
		for(TopicValidationEntity validation : getService.cascadeGetValidation(new TopicValidationCriteria().setTopicId(topic.getId()))){
			cascadeDelete(validation);
		}
		for(PartEntity part : getService.cascadeGetPart(new PartCriteria().setTopicId(topic.getId()))){
			cascadeDelete(part);
		}
		for(QuestionEntity question : getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(topic.getId()))){
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
		for(ParticipationEntity participation : getService.cascadeGetParticipations(new ParticipationCriteria().setChallengeId(challenge.getId()))){
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
		for(ParticipationEntity participation : getService.cascadeGetParticipations(new ParticipationCriteria().setTargetId(team.getId()).setTargetType(SustainappConstantes.TARGET_TEAM))){
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
		for(AnswerEntity answer : getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(question.getId()))){
			cascadeDelete(answer);
		}
		for(AnswerCategoryEntity category : getService.cascadeGetAnswerCateogry(new AnswerCategoryCriteria().setQuestionId(question.getId()))){
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
		for(AnswerEntity answer : getService.cascadeGetAnswer(new AnswerCriteria().setData(answerCategory.getName()))){
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
		for(PlacePictureEntity picture : getService.cascadeGetPlacePictures(new PlacePictureCriteria().setPlaceId(place.getId()))){
			cascadeDelete(picture);
		}
		for(PlaceNoteEntity note : getService.cascadeGetPlaceNotes(new PlaceNoteCriteria().setPlaceId(place.getId()))){
			cascadeDelete(note);
		}
		for(VisitEntity visit : getService.cascadeGetVisit(new VisitCriteria().setPlaceId(place.getId()))){
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
}