package com.ca.sustainapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.comparators.CoursComparator;
import com.ca.sustainapp.comparators.ParticipationComparator;
import com.ca.sustainapp.dao.CourseServiceDAO;
import com.ca.sustainapp.dao.ParticipationServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.responses.LeaderCoursResponse;
import com.ca.sustainapp.responses.LeaderParticipationResponse;
import com.ca.sustainapp.responses.LightProfileResponse;
import com.ca.sustainapp.responses.NewspaperResponse;

/**
 * Restfull controller pour l'affichage du journal
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class NewspaperController extends GenericController {

	/**
	 * Comparators
	 */
	@Autowired
	private CoursComparator coursComparator;
	@Autowired
	private ParticipationComparator participationComparator;
	
	/**
	 * Services
	 */
	@Autowired
	private CourseServiceDAO coursService;
	@Autowired
	private ProfileServiceDAO profilService;
	@Autowired
	private ParticipationServiceDAO participationService;
	@Autowired
	private TeamServiceDAO teamService;
	
	/**
	 * Recevoir toutes les informations pour l'affichage du newspaper
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/newspaper", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getNewspaper(HttpServletRequest request) {
		return new NewspaperResponse()
				.setCours(getLeaderCourses())
				.setParticipations(getLeaderParticipations())
				.setCode(1)
				.buildJson();
	}
	
	/**
 	 * Recupérer la liste des 3 meilleurs cours
	 * @return
	 */
	private List<LeaderCoursResponse> getLeaderCourses(){
		List<LeaderCoursResponse> result = new ArrayList<LeaderCoursResponse>();
		List<CourseEntity> courses = coursService.getAll();
		Collections.sort(courses, coursComparator);
		int i=0;
		for(CourseEntity cours : courses){
			if(cours.getOpen().equals(1) && i<3){
				ProfileEntity profil = profilService.getById(courses.get(i).getCreatorId());
				badgeService.star(profil);
				result.add(new LeaderCoursResponse()
						.setName(courses.get(i).getTitle())
						.setLink("cours/"+courses.get(i).getId())
						.setOwner(new LightProfileResponse(profil))
						);
				 i++;
				 if(i >= 3){
					 break;
				 }
			}
		}
		return result;
	}
	
	/**
	 * Récuperer la liste des 3 meilleures participations
	 * @return
	 */
	private List<LeaderParticipationResponse> getLeaderParticipations(){
		List<LeaderParticipationResponse> result = new ArrayList<LeaderParticipationResponse>();
		List<ParticipationEntity> participations = participationService.getAll();
		Collections.sort(participations, participationComparator);
		int i=0;
		for(ParticipationEntity p : participations){
			if(i < 3){
				if(p.getTargetType().equals(SustainappConstantes.TARGET_PROFILE)){
					badgeService.star(profilService.getById(p.getTargetId()));
				}
				result.add(new LeaderParticipationResponse()
						.setDocument(p.getDocument())
						.setTitle(p.getTitle())
						.setLink("challenges/"+p.getChallengeId())
						.setOwner(p.getTargetType().equals(SustainappConstantes.TARGET_PROFILE) ? 
								new LightProfileResponse(profilService.getById(p.getTargetId()))
								: new LightProfileResponse(teamService.getById(p.getTargetId())))
						);
			i++;
			}else{
				break;
			}
		}
		return result;
	}
	
}