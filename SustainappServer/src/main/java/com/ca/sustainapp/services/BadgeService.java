package com.ca.sustainapp.services;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.criteria.ProfilBadgeCriteria;
import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.dao.ProfilBadgeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.ProfilBadgeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.RankCourseEntity;

/**
 * Service pour la création des badges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 26/04/2017
 * @verion 1.0
 */
@Service("badgeBusinessService")
public class BadgeService {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ProfilBadgeServiceDAO linkService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private TeamServiceDAO teamService;
	@Autowired
	private CascadeGetService getService;
	@Autowired
	private BadgeServiceDAO badgeService;
	
	/**
	 * Verifier si le profil peut obtenir le badge superhero
	 * => avoir réalisé au moins 10 signalements
	 * @param idProfil
	 */
	public boolean superhero(ProfileEntity profil){
		if(getService.cascadeGetReport(new ReportCriteria().setProfilId(profil.getId())).size() >= 10){
			return addByCode(profil, "superhero");
		}
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge teacher
	 * => Avoir au moins 10 notes superieurs à 3 sur ses cours
	 * @param profil
	 */
	public boolean teacher(ProfileEntity profil){
		int votes = 0;
		for(CourseEntity cours : getService.cascadeGetCourses(new CourseCriteria().setCreatorId(profil.getId()))){
			for(RankCourseEntity rank : cours.getListRank()){
				if(rank.getScore() >= 3){
					votes++;
				}
			}
		}
		if(votes >= 10){
			return addByCode(profil, "teacher");
		}
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge graduate
	 * => Avoir validé au moins 10 chapitres
	 * @param idProfil
	 */
	public boolean graduate(ProfileEntity profil){
		if(getService.cascadeGetValidation(new TopicValidationCriteria().setProfilId(profil.getId())).size() >= 10){
			return addByCode(profil, "graduate");
		}
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge capitaine
	 * => Posséder une équipe d'au moins 2 membres en plus du capitaine
	 * @param idProfil
	 */
	public boolean capitaine(ProfileEntity profil){
		return false;
	}

	/**
	 * Verifier si le profil peut obtenir le badge missionary
	 * => Avoir reçu au moins 10 votes dans les challenges
	 * @param idProfil
	 */
	public boolean missionary(ProfileEntity profil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge star
	 * => Apparaitre dans le leaderboard
	 * @param idProfil
	 */
	public boolean star(ProfileEntity profil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge journalist
	 * => Avoir publié au moins 10 articles dans le journal d'actualités
	 * @param idProfil
	 */
	public boolean journalist(ProfileEntity profil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge walker
	 * => Avoir validé ses objectifs de marche
	 * @param idProfil
	 */
	public boolean walker(ProfileEntity profil){
		return false;
	}
	
	/**
	 * get a badge by code
	 * @param code
	 * @return
	 */
	private BadgeEntity getByCode(String code){
		for(BadgeEntity badge : badgeService.getAll()){
			if(badge.getIcon().equals(code)){
				return badge;
			}
		}
		return null;
	}
	
	/**
	 * Add a badge and level if it does not already exist
	 * @param idProfil
	 * @param idBadge
	 */
	private boolean addBadgeIfNotExist(ProfileEntity profil, BadgeEntity badge){
		List<ProfilBadgeEntity> listBadge = getService.cascadeGetProfilBadge(new ProfilBadgeCriteria().setProfilId(profil.getId()));
		for(ProfilBadgeEntity link : listBadge){
			if(link.getBadge().getId().equals(badge.getId())){
				return false;
			}
		}
		ProfilBadgeEntity newLink = new ProfilBadgeEntity()
				.setBadge(badge)
				.setProfilId(profil.getId())
				.setTimestamps(GregorianCalendar.getInstance());
		linkService.createOrUpdate(newLink);
		profileService.createOrUpdate(profil.setLevel(profil.getLevel()+1));
		return true;
	}
	
	/**
	 * Add a badge by code only if code exist
	 * @param profil
	 * @param code
	 */
	private boolean addByCode(ProfileEntity profil, String code){
		BadgeEntity badge = getByCode(code);
		if(null != badge){
			return addBadgeIfNotExist(profil, badge);
		}
		return false;
	}
}