package com.ca.sustainapp.advice;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ca.sustainapp.controllers.GenericController;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.LightProfileResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

/**
 * Advice pour catcher toutes les exceptions techniques.
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/06/2017
 */
@ControllerAdvice
public class ErrorAdvice extends GenericController  {
	
	/**
	 * Injections de dépendances
	 */
	@Autowired
	protected SlackSender slackSender;
	
	/**
	 * Constantes
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorAdvice.class);
	private static final String MAIL_CONTENT = "[ERR_TECH] :sob: mail = %s, profile = %s, message = %s, type = %s, stacktrace = %s";
	private static final List<String> FILTERED_ERRORS_MSG = Arrays.asList(
			"java.io.IOException: Connection reset by peer", "Request method 'HEAD' not supported",
			"java.io.IOException: Broken pipe", "Required String parameter 'term' is not present",
			"Request method 'GET' not supported", "Request method 'POST' not supported");
	private static final String ERR_MSG = "Une exception technique est survenue : message = %s, type = %s";

	/**
	 * capter l'erreur de connexion
	 * @param ex
	 * @param request
	 * @param response
	 */
	@ExceptionHandler(SustainappSecurityException.class)
	@ResponseBody
	public void handleSustainappSecurityException(Exception ex, HttpServletRequest request, HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	/**
	 * Catcher les erreurs pour envoyer dans slack
	 * 
	 * @param ex
	 * @param request
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleAllException(Exception ex, HttpServletRequest request) {
		
		/**
		 * Le message d'erreur
		 */
		String errorMessage = ex.getMessage();
		String errorType = ex.getClass().getSimpleName();
		String stackTrace = getStackTrace(ex);
		LOGGER.error(String.format(ERR_MSG, errorMessage, errorType), ex);

		/**
		 * Erreurs non supportées
		 */
		if (FILTERED_ERRORS_MSG.contains(errorMessage)) {
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		
		/**
		 * Le user responsable
		 */
		UserAccountEntity user = null;
		try {
			user = super.getConnectedUser(request);
		} catch (SustainappSecurityException se) {
			// Nothing to do
		}
		String profileName = (null == user) ? StringUtils.EMPTY : new LightProfileResponse(user.getProfile()).getDenomination();
		String mail = (null == user) ? StringUtils.EMPTY : user.getMail();
		String strMsg = String.format(MAIL_CONTENT, mail, profileName, errorMessage, errorType, stackTrace);

		/**
		 * Envoi sur slack
		 */
		LOGGER.error(strMsg);
		slackSender.sendProdMessage(strMsg);

		return new HttpRESTfullResponse().setCode(0).buildJson();
	}
}