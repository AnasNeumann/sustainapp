
package com.ca.sustainapp.validators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Generic validator for all form
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 10/02/2017
 * @version 1.0
 */
public class GenericValidator {
	
	/**
	 * Validation d'un forumlaire
	 * @param request
	 * @return all errors
	 */
	protected Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = new HashMap<String, String>();
		return result;
	}
	
}
