package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour la modification des informations d'une ville
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Component
public class CityValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("name"))){
			result.put("name", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("phone"))){
			result.put("phone", "form.field.mandatory");
		}
		return result;
	}
}