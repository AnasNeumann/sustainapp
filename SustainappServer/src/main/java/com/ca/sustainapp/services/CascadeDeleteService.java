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
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.entities.ChallengeVoteEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.repositories.AnswerCategoryRepository;
import com.ca.sustainapp.repositories.AnswerRepository;
import com.ca.sustainapp.repositories.ChallengeRepository;
import com.ca.sustainapp.repositories.ChallengeVoteRepository;
import com.ca.sustainapp.repositories.CourseRepository;
import com.ca.sustainapp.repositories.PartRepository;
import com.ca.sustainapp.repositories.ParticipationRepository;
import com.ca.sustainapp.repositories.QuestionRepository;
import com.ca.sustainapp.repositories.RankCourseRepository;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.repositories.TeamRoleRepository;
import com.ca.sustainapp.repositories.TopicRepository;
import com.ca.sustainapp.repositories.TopicValidationRepository;

/**
 * Service pour la suppression en cascade
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2107
 * @verion 1.0
 */
@Service("deleteService")
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

	/**
	 * les services
	 */
	@Autowired
	CascadeGetService getService;
	
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

}