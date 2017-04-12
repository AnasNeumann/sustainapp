package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour le formulaire d'ajout d'une nouvelle question
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/04/2017
 * @version 1.0
 */
@Component
public class QuestionValidator extends GenericValidator {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("message"))){
			result.put("message", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("type"))|| !StringsUtils.parseIntegerQuietly(request.getParameter("type")).isPresent()){
			result.put("type", "form.field.mandatory");
		}
		return result;
	}
}
