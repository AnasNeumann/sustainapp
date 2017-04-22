package com.ca.sustainapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.core.StringStartsWith;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.AnswerResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
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
	 * Récuperer le quiz d'un topic
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getQuiz(HttpServletRequest request) {
		Optional<Long> topic = StringsUtils.parseLongQuickly(request.getParameter("topic"));
		if(!topic.isPresent()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new QuizResponse()
				.setQuestions(getAllTopicQuestions(topic.get()))
				.setCode(1)
				.buildJson();
	}

	/**
	 * Valider finalement le quiz
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String validateQuiz(HttpServletRequest request) {
		Optional<Long> quiz = StringsUtils.parseLongQuickly(request.getParameter("quiz"));
		String answersString = request.getParameter("answers");
		UserAccountEntity user = super.getConnectedUser(request);
		if(null == user || !quiz.isPresent() || answersString.isEmpty()){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		String[] answers =  answersString.split(",");
		List<Boolean> eachQuestions = new ArrayList<Boolean>();
		int i= 0;
		for(QuestionEntity question : getService.cascadeGetQuestion(new QuestionCriteria().setTopicId(quiz.get()))){
			if(null != answers[i]){
				eachQuestions.add(validateQuestion(answers[i].split("/"), question));
			}
		}
		return new ValidationResponse()
				.setAllTrue(!eachQuestions.contains(false))
				.setEachQuestions(eachQuestions)
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * Validation pour chaque question selon le type et les réponses
	 * @param answers
	 * @param question
	 * @return
	 */
	private boolean validateQuestion(String[] answers, QuestionEntity question){
		// TODO validation
		return false;
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