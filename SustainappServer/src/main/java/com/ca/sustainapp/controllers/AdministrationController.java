package com.ca.sustainapp.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ChallengeServiceDAO;
import com.ca.sustainapp.dao.ChallengeTypeServiceDAO;
import com.ca.sustainapp.dao.CityServiceDAO;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.PlaceNoteServiceDAO;
import com.ca.sustainapp.dao.PlacePictureServiceDAO;
import com.ca.sustainapp.dao.PlaceServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.ResearchServiceDAO;
import com.ca.sustainapp.dao.TeamRoleServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.dao.VisitServiceDAO;
import com.ca.sustainapp.entities.ChallengeTypeEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.GenericEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.ResearchEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.VisitEntity;
import com.ca.sustainapp.responses.ChallengesDataResponse;
import com.ca.sustainapp.responses.CityDataResponse;
import com.ca.sustainapp.responses.CoursesDataResponse;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.LightCourseResponse;
import com.ca.sustainapp.responses.ProfilesDataResponse;
import com.ca.sustainapp.responses.ResearchDataResponse;

/**
 * Restfull controller for answers management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class AdministrationController extends GenericController {

	/**
	 * Injection de services DAO
	 */
	@Autowired
	private CourseServiceDAO coursService;
	@Autowired
	private ChallengeServiceDAO challengeService;
	@Autowired
	private TeamServiceDAO teamService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private ResearchServiceDAO searchService;
	@Autowired
	private ChallengeTypeServiceDAO typeService;
	@Autowired
	private ParticipationServiceDAO participationService;
	@Autowired
	private TeamRoleServiceDAO roleService;
	@Autowired
	private CityServiceDAO cityService;
	@Autowired
	private PlaceServiceDAO placeService;
	@Autowired
	private PlacePictureServiceDAO pictureService;
	@Autowired
	private PlaceNoteServiceDAO noteService;
	@Autowired
	private VisitServiceDAO visitService;

	/**
	 * get data for courses
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/courses", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String courses(HttpServletRequest request) {
		if(!super.isAdmin(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		return new CoursesDataResponse()
				.setTotal(coursService.total())
				.setCoursesByCategories(coursesByCategories())
				.setMostSeen(getMostSeenCourses())
				.setCode(1)
				.buildJson();
	}

	/**
	 * get data for challenges
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/challenges", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String challenges(HttpServletRequest request) {
		if(!super.isAdmin(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<ParticipationEntity> participations = participationService.getAll();
		Integer totalChallenges = challengeService.total();
		Integer totalParticipations = participations.size();
		return new ChallengesDataResponse()
				.setTotal(totalChallenges)
				.setChallengesByCategories(challengesByCategories())
				.setAverage(average(totalChallenges, totalParticipations))
				.setUseByDays(useByDays(participations))
				.setUseByHours(useByHours(participations))
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * get data for research
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/research", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String research(HttpServletRequest request) {
		if(!super.isAdmin(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<ResearchEntity> research = searchService.getAll();
		return new ResearchDataResponse()
				.setTotal(research.size())
				.setMostSeen(searchService.mostSeen())
				.setUseByDays(useByDays(research))
				.setUseByHours(useByHours(research))
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * get data for profiles
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/profiles", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String profiles(HttpServletRequest request) {
		if(!super.isAdmin(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		List<ProfileEntity> profiles = profileService.getAll();
		List<TeamEntity> teams = teamService.getAll();
		Float visibles = getPercentageVisibility(profiles);
		return new ProfilesDataResponse()
				.setTotalProfiles(profiles.size())
				.setTotalTeams(teams.size())
				.setAverage(average(teams.size(), roleService.total()))
				.setProfileByAge(profileByAge(profiles))
				.setProfileByLevel(profileByLevel(profiles))
				.setTeamByLevel(teamByLevel(teams))
				.setPercentageVisible(visibles)
				.setPercentageNotVisible(new Float(100-visibles))
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * get data for cities
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/administration/cities", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String cities(HttpServletRequest request) {
		if(!super.isAdmin(request)){
			return new HttpRESTfullResponse().setCode(0).buildJson();
		}
		Integer totalPlace = placeService.total();
		List<VisitEntity> visits = visitService.getAll();
		return new CityDataResponse()
				.setAveragePictures(average(totalPlace, pictureService.total()))
				.setMoreViews(mostVisitedPlaces(5))
				.setPlaceByNotes(placeByNotes())
				.setTotalCities(cityService.total())
				.setTotalPlaces(totalPlace)
				.setVisitByDays(useByDays(visits))
				.setVisitByHours(useByHours(visits))
				.setCode(1)
				.buildJson();
	}
	
	/**
	 * Obtenir le pourcentage de profils visibles
	 * @param profiles
	 * @return
	 */
	private Float getPercentageVisibility(List<ProfileEntity> profiles){
		if(profiles.size() <= 0){
			return 0F;
		}
		Integer total = 0;
		for(ProfileEntity profile : profiles){
			if(profile.getVisibility().equals(1)){
				total++;
			}
		}
		return new Float(total * 100 / profiles.size());
	}
	
	/**
	 * Get places by notes
	 * @return
	 */
	private Map<Integer, Integer> placeByNotes(){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for(int i=0; i<=5; i++){
			result.put(i, noteService.countByScore(i));
		}
		return result;
	}
	
	/**
	 * Get the x most visited places
	 * @param maxResult
	 * @return
	 */
	 private List<PlaceEntity> mostVisitedPlaces(Integer maxResult){
		 List<PlaceEntity> result = new ArrayList<PlaceEntity>();
		 List<Long> ids = visitService.mostSeen(maxResult);
		 for(Long id : ids){
			 PlaceEntity place = placeService.getById(id);
			 if(null != place){
				 result.add(place);
			 }
		 }
		 return result;
	 }
	
	/**
	 * get profiles number by level
	 * @param profiles
	 * @return
	 */
	private Map<Integer, Integer> profileByLevel(List<ProfileEntity> profiles){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for(ProfileEntity profile : profiles){
			Integer level = profile.getLevel();
			if(null != result.get(level)){
				result.put(level, result.get(level)+1);
			} else {
				result.put(level, 1);
			}
		}
		return result;
	}
	
	/**
	 * get teams number by level
	 * @param teams
	 * @return
	 */
	private Map<Integer, Integer> teamByLevel(List<TeamEntity> teams){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for(TeamEntity team : teams){
			Integer level = team.getLevel();
			if(null != result.get(level)){
				result.put(level, result.get(level)+1);
			} else {
				result.put(level, 1);
			}
		}
		return result;
	}
	
	/**
	 * Get profiles number by age
	 * @param profiles
	 * @return
	 */
	private Map<Integer, Integer> profileByAge(List<ProfileEntity> profiles){
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for(ProfileEntity profile : profiles){
			if(null != profile.getBornDate()){
				Integer year = GregorianCalendar.getInstance().get(Calendar.YEAR) - profile.getBornDate().get(Calendar.YEAR);
				if(null != result.get(year)){
					result.put(year, result.get(year)+1);
				} else {
					result.put(year, 1);
				}
			}
		}
		return result;
	}
	
	/**
	 * Trier les cours par catégories
	 * @return
	 */
	private Map<String, Integer> coursesByCategories(){
		Map<String, Integer> result = new HashMap<String, Integer>();
		for(ChallengeTypeEntity type : typeService.getAll()){
			result.put(type.getName(), coursService.countByType(type.getId()));
		}
		return result;
	}

	/**
	 * Trier les challenges par catégories
	 * @return
	 */
	private Map<String, Integer> challengesByCategories(){
		Map<String, Integer> result = new HashMap<String, Integer>();
		for(ChallengeTypeEntity type : typeService.getAll()){
			result.put(type.getName(), challengeService.countByType(type.getId()));
		}
		return result;
	}
	
	/**
	 * get the most seen courses
	 * @return
	 */
	private List<LightCourseResponse>getMostSeenCourses(){
		List<LightCourseResponse> result = new ArrayList<LightCourseResponse>();
		for(CourseEntity entity : coursService.mostSeen()){
			result.add(new LightCourseResponse(entity));
		}
		return result;
	}

	/**
	 * Calculer une moyenne sur 2 tailles
	 * @param denominateur
	 * @param numerateur
	 * @return
	 */
	private Float average(Integer denominateur, Integer numerateur){
		if(null == denominateur || null == numerateur || 0 == denominateur || 0 == numerateur){
			return 0F;
		}
		return new Float(numerateur/denominateur);
	}
	
	/**
	 * get the entity number by Hours
	 * @param entity
	 * @return
	 */
	private Map<String, Integer> useByHours(List<? extends GenericEntity> entities){
		Map<String, Integer> result = initMapHous();
		for(GenericEntity entity : entities){
			String hours = String.valueOf(entity.getTimestamps().get(Calendar.HOUR_OF_DAY));
			result.put(hours, result.get(hours)+1);
		}
		return result;
	}
	
	/**
	 * get the entity number by Days
	 * @param entity
	 * @return
	 */
	private Map<String, Integer> useByDays(List<? extends GenericEntity> entities){
		Map<String, Integer> result = initMapDays();
		for(GenericEntity entity : entities){
			String day = entity.getTimestamps().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
			result.put(day, result.get(day)+1);
		}
		return result;
	}
	
	/**
	 * Initialiser la map pour les heures
	 * @return
	 */
	private Map<String, Integer> initMapHous(){
		Map<String, Integer> result = new HashMap<String, Integer>();
		for(int i=0; i<24; i++){
			result.put(String.valueOf(i), 0);
		}
		return result;
	}
	
	/**
	 * Initialiser la map pour les jours
	 * @return
	 */
	private Map<String, Integer> initMapDays(){
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("Monday", 0);
		result.put("Tuesday", 0);
		result.put("Wednesday", 0);
		result.put("Thursday", 0);
		result.put("Friday", 0);
		result.put("Saturday", 0);
		result.put("Sunday", 0);
		return result;
	}
}