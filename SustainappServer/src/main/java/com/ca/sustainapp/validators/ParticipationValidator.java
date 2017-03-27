package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.utils.StringsUtils;

/**
 * Validator pour l'ajout d'une participation
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/03/2017
 * @version 1.0
 */
@Component
public class ParticipationValidator extends GenericValidator {
	
	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private TeamServiceDAO teamService;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("title"))){
			result.put("title", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("about"))){
			result.put("about", "form.about.mandatory");
		}
		if(isEmpty(request.getParameter("targetType")) || isEmpty(request.getParameter("targetId")) || !StringsUtils.parseLongQuickly(request.getParameter("targetId")).isPresent()){
			result.put("target", "form.field.mandatory");
		}
		if(isEmpty(request.getParameter("challenge")) || !StringsUtils.parseLongQuickly(request.getParameter("challenge")).isPresent()){
			result.put("challenge", "form.field.mandatory");
		} else {
			Integer levelMin = challengeService.getById(StringsUtils.parseLongQuickly(request.getParameter("challenge")).get()).getMinLevel();
			if(request.getParameter("targetType").equals(SustainappConstantes.TARGET_TEAM)){
				TeamEntity team = teamService.getById(StringsUtils.parseLongQuickly(request.getParameter("targetId")).get());
				if(team.getLevel() < levelMin){
					result.put("level", "form.level.min");
				}
			} else {
				ProfileEntity profile = profileService.getById(StringsUtils.parseLongQuickly(request.getParameter("targetId")).get());
				if(profile.getLevel() < levelMin){
					result.put("level", "form.level.min");
				}
			}
		}		
		return result;
	}
	
}
