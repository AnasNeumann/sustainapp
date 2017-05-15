package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour la création d'un nouvel éco-lieu
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
@Component
public class PlaceValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("name"))){
			result.put("name", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("about"))){
			result.put("about", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("latitude")) || !StringsUtils.parseFloatQuiclky(request.getParameter("latitude")).isPresent()){
			result.put("latitude", "form.field.mandatory");
		}                                
		if(isEmpty(request.getParameter("longitude")) || !StringsUtils.parseFloatQuiclky(request.getParameter("longitude")).isPresent()){
			result.put("longitude", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("city")) || !StringsUtils.parseLongQuickly(request.getParameter("city")).isPresent()){
			result.put("city", "form.field.mandatory");
		}
		return result;
	}
	
}