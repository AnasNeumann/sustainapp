package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour le formulaire de modification du profil
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/02/2017
 * @version 1.0
 */
@Component
public class ProfileValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("firstName"))){
			result.put("mail", "form.firstName.mandatory");
		}
		if(isEmpty(request.getParameter("lastName"))){
			result.put("password", "form.lastName.mandatory");
		}
		return result;
	}

}
