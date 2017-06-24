package com.ca.sustainapp.controllers;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.services.CascadeGetService;
import com.ca.sustainapp.utils.JsonUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ProfileValidator;

/**
 * Juste un controller de récupération de données en base pour tester
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/06/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ToCorrectController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private ProfileValidator profileValidator;
	@Autowired
	private CascadeGetService getService;	
	@Autowired
	private BadgeServiceDAO badgeService;
	
	/**
	 * get a profile by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/correction/profile", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> get(HttpServletRequest request) {
		Optional<Long> id = StringsUtils.parseLongQuickly(request.getParameter("id"));
		ProfileEntity profile = profileService.getById(id.get());
		return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS))
                .body(JsonUtils.objectTojsonQuietly(profile, ProfileEntity.class));
	}
	
}
