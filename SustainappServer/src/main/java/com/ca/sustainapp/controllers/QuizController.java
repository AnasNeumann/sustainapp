package com.ca.sustainapp.controllers;

import java.util.ArrayList;
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
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.TopicValidationServiceDAO;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.AnswerResponse;
import com.ca.sustainapp.responses.QuestionQuizResponse;
import com.ca.sustainapp.responses.QuizResponse;
import com.ca.sustainapp.responses.ValidationResponse;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Restfull controller for quiz validation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class QuizController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private TopicValidationServiceDAO service;
	
	/**
	 * Récuperer le quiz d'un topic
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getQuiz(HttpServletRequest request) {
		Optional<Long> topic = StringsUtils.parseLongQuickly(request.getParameter("topic"));
		if(!topic.isPresent()){
			return super.refuse();
		}
		return super.success(new QuizResponse()
				.setQuestions(getAllTopicQuestions(topic.get())));
	}

	/**
	 * Valider finalement le quiz
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> validateQuiz(HttpServletRequest request) {
		Optional<Long> quiz = StringsUtils.parseLongQuickly(request.getParameter("quiz"));
		String answersString = request.getParameter("answers");
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !quiz.isPresent() || answersString.isEmpty()){
			return super.refuse();
		}
		String[] answers =  answersString.split(",");
		List<Boolean> eachQuestions = new ArrayList<Boolean>();
		int i= 0;
		for(QuestionEntity question : getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(quiz.get()))){
			if(null != answers[i]){
				eachQuestions.add(validateQuestion(answers[i].split("/"), question));
			}
			i++;
		}
		List<TopicValidationEntity> validations = getService.cascadeGetValidation(new TopicValidationCriteria().setProfilId(user.getProfile().getId()).setTopicId(quiz.get()));
		if(!eachQuestions.contains(false) && validations.isEmpty()){
			service.createOrUpdate(new TopicValidationEntity().setProfilId(user.getProfile().getId()).setTopicId(quiz.get()).setTimestamps(GregorianCalendar.getInstance()));
			badgeService.graduate(user.getProfile());
		}
		return super.success(new ValidationResponse()
				.setAllTrue(!eachQuestions.contains(false))
				.setEachQuestions(eachQuestions));
	}
	
	/**
	 * Validation pour chaque question selon le type et les réponses
	 * @param answers
	 * @param question
	 * @return
	 */
	private boolean validateQuestion(String[] answers, QuestionEntity question){
		int i = 0;
		for(AnswerEntity answer : getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(question.getId()))){
			if(!answer.getData().equals(answers[i])){
				return false;
			}
			i++;
		}
		return true;
	}

	/**
	 * getAll questions of a topic
	 * @param id
	 * @return
	 */
	List<QuestionQuizResponse> getAllTopicQuestions(Long id){
		List<QuestionQuizResponse> result = new SustainappList<QuestionQuizResponse>();
		for(QuestionEntity question : getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(id))){
			QuestionQuizResponse response = new QuestionQuizResponse(question)
					.setCategories(getService.cascadeGetAnswerCateogry(new AnswerCategoryCriteria().setQuestionId(question.getId())))
					.setAnswers(getAllQuestionAnswers(question.getId()));
				result.add(response);
		}
		return result;
	}

	/**
	 * getAll answers of a question
	 * @param id
	 * @return
	 */
	List<AnswerResponse> getAllQuestionAnswers(Long id){
		List<AnswerResponse> result = new SustainappList<AnswerResponse>();
		for(AnswerEntity answer : getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(id))){
				result.add(new AnswerResponse(answer));
		}
		return result;
	}
}