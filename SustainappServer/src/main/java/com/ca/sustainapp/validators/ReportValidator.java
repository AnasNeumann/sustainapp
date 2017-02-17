package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Validator pour l'ajout d'un signalement
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/02/2017
 * @version 1.0
 */
@Component
public class ReportValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("file"))){
			result.put("file", "form.file.mandatory");
		}
		if(isEmpty(request.getParameter("message"))){
			result.put("message", "form.message.mandatory");
		}
		return result;
	}

}
