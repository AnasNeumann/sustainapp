package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour l'ajout et modification d'un type de challenge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/03/2017
 * @version 1.0
 */
@Component
public class ChallengeTypeValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("name"))){
			result.put("name", "form.name.mandatory");
		}
		return result;
	}
	
}
