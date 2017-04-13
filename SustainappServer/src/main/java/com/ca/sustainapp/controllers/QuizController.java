package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Restfull controller for quiz validation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class QuizController {

	/**
	 * Récuperer le quiz d'un topic
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getQuiz(HttpServletRequest request) {
		return null;
	}

	/**
	 * Valider finalement le quiz
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quiz", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String validateQuiz(HttpServletRequest request) {
		return null;
	}
	
}
