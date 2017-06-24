package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.entities.AnswerEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.AnswerValidator;

/**
 * Restfull controller for answers management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class AnswerController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	AnswerValidator validator;
	
	/**
	 * create a new answer
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> create(HttpServletRequest request) {
		QuestionEntity question = super.getQuestionIfOwner(request);
		if(null == question){
			return super.refuse();
		}
		List<AnswerEntity> answers = getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(question.getId()));
		Integer numero = (null != answers)? answers.size() : 0;
		AnswerEntity answer = new AnswerEntity()
				.setData(request.getParameter("data"))
				.setMessage(request.getParameter("message"))
				.setNumero(numero)
				.setQuestionId(question.getId())
				.setTimestamps(GregorianCalendar.getInstance());
		if(!isEmpty(request.getParameter("file"))){
			answer.setPicture(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG));
		}
		if(answer.getData().isEmpty()){
			answer.setData(answer.getNumero().toString());
		}
		return super.success(new IdResponse().setId(answerService.createOrUpdate(answer)));
	}
	
	/**
	 * delete a answer by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		AnswerEntity answer = super.getAnswerIfOwner(request);
		if(null == answer){
			return super.refuse();
		}
		List<AnswerEntity> answers = getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(answer.getQuestionId()));
		for(AnswerEntity a : answers){
			if(a.getNumero() > answer.getNumero()){
				answerService.createOrUpdate(a.setNumero(a.getNumero()-1));
			}
		}
		deleteService.cascadeDelete(answer);
		return super.success();
	}	

	/**
	 * drag & drop a answer
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/answer/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> drop(HttpServletRequest request) {
		AnswerEntity answer = super.getAnswerIfOwner(request);
		Optional<Integer> toIndex = StringsUtils.parseIntegerQuietly(request.getParameter("position"));
		if(null == answer || !toIndex.isPresent()){
			return super.refuse();
		}
		List<AnswerEntity> answers = getService.cascadeGetAnswer(new AnswerCriteria().setQuestionId(answer.getQuestionId()));
		if(toIndex.get() > answer.getNumero()){
			avancer(answer, answers, toIndex.get());
		}else if(toIndex.get() < answer.getNumero()){
			reculer(answer, answers, toIndex.get());
		}
		return super.success();
	}
	
	/**
	 * Avancer une answer dans la list
	 * @param answer
	 * @param answers
	 * @param arrivee
	 */
	private void avancer(AnswerEntity answer, List<AnswerEntity> answers, Integer arrivee){
		for(AnswerEntity a : answers){
			if(a.getNumero() <= arrivee && a.getNumero() > answer.getNumero()){
				answerService.createOrUpdate(a.setNumero(a.getNumero()-1));
			}
		}
		answerService.createOrUpdate(answer.setNumero(arrivee));
	}
	
	/**
	 * reculer une answer dans la liste
	 * @param answer
	 * @param answers
	 * @param arrivee
	 */
	private void reculer(AnswerEntity answer, List<AnswerEntity> answers, Integer arrivee){
		for(AnswerEntity a : answers){
			if(a.getNumero() >= arrivee && a.getNumero() < answer.getNumero()){
				answerService.createOrUpdate(a.setNumero(a.getNumero()+1));
			}
		}
		answerService.createOrUpdate(answer.setNumero(arrivee));
	}
}