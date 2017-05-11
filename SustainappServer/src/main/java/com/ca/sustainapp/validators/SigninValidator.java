package com.ca.sustainapp.validators;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.criteria.UserAccountCriteria;
import com.ca.sustainapp.dao.UserAccountServiceDAO;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour le formulaire d'inscription
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/02/2017
 * @version 1.0
 */
@Component
public class SigninValidator extends GenericValidator {

	/**
	 * Service d'accès à la base de données
	 */
	@Autowired
	private UserAccountServiceDAO service;
	
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
		if(isEmpty(request.getParameter("password")) || request.getParameter("password").length() < 4){
			result.put("password", "form.password.mandatory");
		}
		if(isEmpty(request.getParameter("firstName"))){
			result.put("firstName", "form.firstName.mandatory");
		}
		if(isEmpty(request.getParameter("lastName"))){
			result.put("lastName", "form.lastName.mandatory");
		}
		Optional<Integer> type = StringsUtils.parseIntegerQuietly(request.getParameter("type"));
		if(!type.isPresent()){
			result.put("type", "form.field.mandatory");
			return result;
		}
		if(type.get().equals(1) && isEmpty(request.getParameter("phone"))){
			result.put("phone", "form.field.mandatory");
		}
		if(type.get().equals(1) && isEmpty(request.getParameter("city"))){
			result.put("city", "form.field.mandatory");
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
		SearchResult<UserAccountEntity> listResult = service.searchByCriteres(new UserAccountCriteria().setMail(mail), 0L, 100L);
		if(null != listResult.getResults()){
			for(UserAccountEntity user : listResult.getResults()){
				if(user.getMail().equals(mail)){
					return true;
				}
			}
		}
		return false;
	}
	
}
