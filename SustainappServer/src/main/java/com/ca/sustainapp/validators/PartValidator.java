package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour l'ajout d'un nouveau chapitre
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/04/2017
 * @version 1.0
 */
@Component
public class PartValidator extends GenericValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		Optional<Integer> type = StringsUtils.parseIntegerQuietly(request.getParameter("type"));
		if(!type.isPresent()){
			result.put("type", "form.field.mandatory");
			return result;
		}
		switch (type.get()) {
			case 1:
				if(isEmpty(request.getParameter("title")) && isEmpty(request.getParameter("content"))){
					result.put("title", "form.field.mandatory");
					result.put("content", "form.field.mandatory");
				}
				break;
			case 2:
				if(isEmpty(request.getParameter("title"))){
					result.put("title", "form.field.mandatory");
				}
				if(isEmpty(request.getParameter("file"))){
					result.put("file", "form.field.mandatory");
				}
				break;
			case 3:
				if(isEmpty(request.getParameter("title"))){
					result.put("title", "form.field.mandatory");
				}
				if(isEmpty(request.getParameter("video"))){
					result.put("video", "form.field.mandatory");
				}
				break;
			case 4:
				if(isEmpty(request.getParameter("link"))){
					result.put("link", "form.field.mandatory");
				}
				break;
			default :
				result.put("type", "form.field.mandatory");
				break;
		}
		return result;
	}
	
}
