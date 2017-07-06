package com.ca.sustainapp.controllers;

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
import com.ca.sustainapp.entities.AnswerCategoryEntity;
import com.ca.sustainapp.entities.QuestionEntity;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.AnswerValidator;

/**
 * Restfull controller for answer's categories management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class AnswerCategoryController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	AnswerValidator validator;
	
	/**
	 * create a new category
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/category", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> create(HttpServletRequest request) {
		QuestionEntity question = super.getQuestionIfOwner(request);
		if(null == question){
			return super.refuse();
		}
		List<AnswerCategoryEntity> categories = getService.cascadeGet(new AnswerCategoryCriteria().setQuestionId(question.getId()));
		Integer numero = (null != categories)? categories.size() : 0;
		AnswerCategoryEntity category = new AnswerCategoryEntity()
				.setName(request.getParameter("message"))
				.setNumero(numero)
				.setQuestionId(question.getId())
				.setTimestamps(GregorianCalendar.getInstance());
		return super.success(new IdResponse().setId(answerCategoryService.createOrUpdate(category)));
	}
	
	/**
	 * delete a category by id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/category/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		AnswerCategoryEntity category = super.getAnswerCategoryIfOwner(request);
		if(null == category){
			return super.refuse();
		}
		List<AnswerCategoryEntity> categories = getService.cascadeGet(new AnswerCategoryCriteria().setQuestionId(category.getQuestionId()));
		for(AnswerCategoryEntity c : categories){
			if(c.getNumero() > category.getNumero()){
				answerCategoryService.createOrUpdate(c.setNumero(c.getNumero()-1));
			}
		}
		deleteService.cascadeDelete(category);
		return super.success();
	}
	
	/**
	 * drag & drop a category
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/category/drop", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> drop(HttpServletRequest request) {
		AnswerCategoryEntity category = super.getAnswerCategoryIfOwner(request);
		Optional<Integer> toIndex = StringsUtils.parseIntegerQuietly(request.getParameter("position"));
		if(null == category || !toIndex.isPresent()){
			return super.refuse();
		}
		List<AnswerCategoryEntity> categories = getService.cascadeGet(new AnswerCategoryCriteria().setQuestionId(category.getQuestionId()));
		if(toIndex.get() > category.getNumero()){
			avancer(category, categories, toIndex.get());
		}else if(toIndex.get() < category.getNumero()){
			reculer(category, categories, toIndex.get());
		}
		return super.success();
	}
	
	/**
	 * Avancer une answerCategory dans la list
	 * @param answerCategory
	 * @param answerCategories
	 * @param arrivee
	 */
	private void avancer(AnswerCategoryEntity answerCategory, List<AnswerCategoryEntity> answerCategories, Integer arrivee){
		for(AnswerCategoryEntity a : answerCategories){
			if(a.getNumero() <= arrivee && a.getNumero() > answerCategory.getNumero()){
				answerCategoryService.createOrUpdate(a.setNumero(a.getNumero()-1));
			}
		}
		answerCategoryService.createOrUpdate(answerCategory.setNumero(arrivee));
	}
	
	/**
	 * reculer une answerCategory dans la liste
	 * @param answerCategory
	 * @param answerCategories
	 * @param arrivee
	 */
	private void reculer(AnswerCategoryEntity answerCategory, List<AnswerCategoryEntity> answerCategories, Integer arrivee){
		for(AnswerCategoryEntity a : answerCategories){
			if(a.getNumero() >= arrivee && a.getNumero() < answerCategory.getNumero()){
				answerCategoryService.createOrUpdate(a.setNumero(a.getNumero()+1));
			}
		}
		answerCategoryService.createOrUpdate(answerCategory.setNumero(arrivee));
	}
}