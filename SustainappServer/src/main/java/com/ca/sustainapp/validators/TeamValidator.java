package com.ca.sustainapp.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.criteria.TeamCriteria;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SearchResult;

/**
 * Validator pour l'ajout et modification d'une equipe
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
@Component
public class TeamValidator extends GenericValidator {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private TeamServiceDAO service;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> validate(HttpServletRequest request){
		Map<String, String> result = super.validate(request);
		if(isEmpty(request.getParameter("name"))){
			result.put("name", "form.name.mandatory");
		} else if(exitName(request.getParameter("name"))){
			result.put("name", "form.name.exist");
		}
		return result;
	}
	
	/**
	 * Verification de l'existance du nom d'équipe
	 * @param name
	 * @return
	 */
	private boolean exitName(String name){
		SearchResult<TeamEntity> listResult = service.searchByCriteres(new TeamCriteria().setName(name), 0L, 100L);
		if(null != listResult.getResults()){
			for(TeamEntity team : listResult.getResults()){
				if(team.getName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
}
