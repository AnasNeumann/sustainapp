package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour l'ajout et modification d'un challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
@Component
public class ChallengeValidator extends GenericValidator {

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
		return result;
	}
}