package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour l'ajout d'un challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
@Component
public class ChallengeValidator extends GenericValidator {
	
	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ChallengeTypeServiceDAO challengeTypeService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("name"))){
			result.put("name", "form.name.mandatory");
		}
		if(isEmpty(request.getParameter("about"))){
			result.put("about", "form.about.mandatory");
		}
		if(isEmpty(request.getParameter("endDate"))){
			result.put("endDate", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("levelMin")) || !StringsUtils.parseIntegerQuietly(request.getParameter("levelMin")).isPresent()){
			result.put("levelMin", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("type")) || !StringsUtils.parseLongQuietly(request.getParameter("type")).isPresent()){
			result.put("type", "form.field.mandatory");
		} else if(null == challengeTypeService.getById(StringsUtils.parseLongQuietly(request.getParameter("type")).get())){
			result.put("type", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("teamEnabled"))){
			result.put("teamEnabled", "form.field.mandatory");
		}
		return result;
	}
}