package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour l'ajout d'un nouveau chapitre
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/04/2017
 * @version 1.0
 */
@Component
public class TopicValidator extends GenericValidator {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("title"))){
			result.put("title", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("about"))){
			result.put("about", "form.about.mandatory");
		}
		return result;
	}
}
