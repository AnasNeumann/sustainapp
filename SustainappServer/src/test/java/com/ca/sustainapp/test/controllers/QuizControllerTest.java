package com.ca.sustainapp.test.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.controllers.QuizController;
import com.ca.sustainapp.criteria.AnswerCategoryCriteria;
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.TopicValidationServiceDAO;
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.entities.TopicValidationEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.test.boot.AbstractTest;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;

/**
 * Classe de test pour le controller du quizz
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/05/2017
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class QuizControllerTest extends AbstractTest {

	/**
	 * Mocks
	 */
	@Mock
	private TopicValidationServiceDAO topicValidationServiceMock;
	
	/**
	 * le controller a tester
	 */
	@InjectMocks
	private QuizController controller;
	
	/**
	 * JDD
	 */
	QuestionEntity question;
	AnswerCategoryEntity category;
	AnswerEntity answer;
	
	/**
	 * Initiation du jeu de données
	 */
	@Before
	public final void init() {
		super.init(controller);
		question = new QuestionEntity()
				.setId(GENERIC_ID)
				.setMessage("Un message")
				.setNumero(1)
				.setTopicId(GENERIC_ID)
				.setType(SustainappConstantes.QUESTION_TYPE_CLASSIFICATION)
				.setTimestamps(GregorianCalendar.getInstance());
		category = new AnswerCategoryEntity()
				.setId(GENERIC_ID)
				.setName(GENERIC_NAME_OR_TITLE)
				.setNumero(1)
				.setQuestionId(GENERIC_ID)
				.setTimestamps(GregorianCalendar.getInstance());
		answer = new AnswerEntity()
				.setData(GENERIC_NAME_OR_TITLE)
				.setQuestionId(GENERIC_ID)
				.setNumero(1)
				.setTimestamps(GregorianCalendar.getInstance());
		when(requestMock.getParameter("topic")).thenReturn(super.GENERIC_ID.toString());
		when(requestMock.getParameter("quiz")).thenReturn(super.GENERIC_ID.toString());		
		when(getServiceMock.cascadeGetQuestion(any(QuestionCriteria.class))).thenReturn(
				new SustainappList<QuestionEntity>().put(question));
		when(getServiceMock.cascadeGetAnswerCateogry(any(AnswerCategoryCriteria.class))).thenReturn(
				new SustainappList<AnswerCategoryEntity>().put(category));
		when(getServiceMock.cascadeGetAnswer(any(AnswerCriteria.class))).thenReturn(
				new SustainappList<AnswerEntity>().put(answer));
	}
	
	/**
	 * test de récupération d'un quizz
	 */
	@Test
	public void getQuizTest() {
		controller.getQuiz(requestMock);
		verify(getServiceMock, times(1)).cascadeGetQuestion(any(QuestionCriteria.class));
		verify(getServiceMock, times(1)).cascadeGetAnswerCateogry(any(AnswerCategoryCriteria.class));
		verify(getServiceMock, times(1)).cascadeGetAnswer(any(AnswerCriteria.class));		
	}

	/**
	 * Test de validation avec aucune erreur du quizz
	 */
	@Test
	public void validateQuizSuccessTest(){
		when(requestMock.getParameter("answers")).thenReturn(GENERIC_NAME_OR_TITLE);
		when(getServiceMock.cascadeGetValidation(any(TopicValidationCriteria.class))).thenReturn(
				new SustainappList<TopicValidationEntity>());
		when(topicValidationServiceMock.createOrUpdate(any(TopicValidationEntity.class))).thenReturn(GENERIC_ID);
		controller.validateQuiz(requestMock);
		verify(getServiceMock, times(1)).cascadeGetValidation(any(TopicValidationCriteria.class));
		verify(topicValidationServiceMock, times(1)).createOrUpdate(any(TopicValidationEntity.class));
	}

	/**
	 * Test de validation avec erreur du quizz
	 */
	@Test
	public void validateQuizErrorTest(){
		when(requestMock.getParameter("answers")).thenReturn("otherName");
		when(getServiceMock.cascadeGetValidation(any(TopicValidationCriteria.class))).thenReturn(
				new SustainappList<TopicValidationEntity>());
		when(topicValidationServiceMock.createOrUpdate(any(TopicValidationEntity.class))).thenReturn(GENERIC_ID);
		controller.validateQuiz(requestMock);
		verify(getServiceMock, times(1)).cascadeGetValidation(any(TopicValidationCriteria.class));
		verify(topicValidationServiceMock, times(0)).createOrUpdate(any(TopicValidationEntity.class));
	}

}