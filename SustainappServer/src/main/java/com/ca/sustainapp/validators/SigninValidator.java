package com.ca.sustainapp.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.criteria.ProfileCriteria;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SearchResult;

/**
 * Validator pour le formulaire d'inscription
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 *
 */
@Component
public class SigninValidator extends GenericValidator {

	/**
	 * Service d'accès à la base de données
	 */
	@Autowired
	private ProfileServiceDAO service;
	
	/**
	 * Variables
	 */
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("mail"))){
			result.put("mail", "form.mail.mandatory");
		} else if(!isValidMail(request.getParameter("mail"))){
			result.put("mail", "form.mail.format");
		} else if(exitMail(request.getParameter("mail"))){
			result.put("mail", "form.mail.exist");
		}
		if(isEmpty(request.getParameter("password"))){
			result.put("password", "form.password.mandatory");
		}
		if(isEmpty(request.getParameter("firstName"))){
			result.put("firstName", "form.firstName.mandatory");
		}
		if(isEmpty(request.getParameter("lastName"))){
			result.put("lastName", "form.lastName.mandatory");
		}
		return result;
	}
	
	/**
	 * Fonction de verification si le mail est valide
	 * @param mail
	 * @return
	 */
	public boolean isValidMail(final String mail){
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(mail);
		return matcher.matches();
	}
	
	/**
	 * Fonction de verification si le mail n'existe pas en base de données
	 * @param mail
	 * @return
	 */
	private boolean exitMail(String mail){
		SearchResult<ProfileEntity> listResult = service.searchByCriteres(new ProfileCriteria().setMail(mail), 0L, 100L);
		if(null != listResult.getResults()){
			for(ProfileEntity profile : listResult.getResults()){
				if(profile.getMail().equals(mail)){
					return false;
				}
			}
		}
		return false;
	}
	
}
