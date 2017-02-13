package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour le formulaire de connexion
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/02/2017
 * @version 1.0
 */
@Component
public class LoginValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("mail"))){
			result.put("mail", "form.mail.mandatory");
		}
		if(isEmpty(request.getParameter("password"))){
			result.put("password", "form.password.mandatory");
		}
		return result;
	}
	
}
