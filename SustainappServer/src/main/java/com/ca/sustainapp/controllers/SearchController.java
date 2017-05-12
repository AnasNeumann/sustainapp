package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.ResearchServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ResearchEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.LightCityResponse;
import com.ca.sustainapp.responses.LightCourseResponse;
import com.ca.sustainapp.responses.SearchResponse;

/**
 * Restfull controller for profiles and teams research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/03/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class SearchController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private TeamServiceDAO teamService;
	@Autowired
	private CourseServiceDAO coursService;
	@Autowired
	private ResearchServiceDAO searchService;
	@Autowired
	private CityServiceDAO cityService;
	
	
	/**
	 * search profiles and teams
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String search(HttpServletRequest request) {
		String query = request.getParameter("query");
		if(isEmpty(query)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new SearchResponse()
				.setProfiles(profileService.searchByFulName(query,5))
				.setTeams(teamService.searchByKeywords(query, 5))
				.setCities(searchLightCities(query, 5))
				.setCourses(searchLightCourses(query, 5))
				.setCode(1)
				.buildJson();
	}

	/**
	 * save a research into database
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search/save", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	private String save(HttpServletRequest request){
		UserAccountEntity user = super.getConnectedUser(request);
		String query = request.getParameter("query");
		if(isEmpty(query) || null == user){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		searchService.createOrUpdate(
				new ResearchEntity()
					.setProfilId(user.getProfile().getId())
					.setQuery(query)
					.setTimestamps(GregorianCalendar.getInstance())
				);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * Recherche des 5 villes proches en nom de la recherche
	 * @param query
	 * @param maxResult
	 * @return
	 */
	private List<LightCityResponse> searchLightCities(String query, Integer maxResult){
		List<LightCityResponse> result = new SustainappList<LightCityResponse>();
		for(CityEntity city : cityService.searchByKeywords(query, 5)){
			result.add(new LightCityResponse()
					.setId(city.getId())
					.setName(city.getName())
					.setPhone(city.getPhone())
					);	
		}
		return result;
	}
	
	/**
	 * Search courses by keyword
	 * @param query
	 * @param maxResult
	 * @return
	 */
	private List<LightCourseResponse> searchLightCourses(String query, Integer maxResult){
		List<CourseEntity> allCourses = coursService.searchByKeywords(query, maxResult);
		List<LightCourseResponse> result = new SustainappList<LightCourseResponse>();
		for(CourseEntity cours : allCourses){
			result.add(new LightCourseResponse(cours));
		}
		return result;
	}
}