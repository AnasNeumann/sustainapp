package com.ca.sustainapp.services;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.ProfilBadgeCriteria;
import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.dao.ProfilBadgeServiceDAO;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.TeamServiceDAO;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.entities.ProfilBadgeEntity;
import com.ca.sustainapp.entities.ProfileEntity;

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
	 * @param idProfil
	 */
	public boolean teacher(Long idProfil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge graduate
	 * @param idProfil
	 */
	public boolean graduate(Long idProfil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge capitaine
	 * @param idProfil
	 */
	public boolean capitaine(Long idProfil){
		return false;
	}

	/**
	 * Verifier si le profil peut obtenir le badge missionary
	 * @param idProfil
	 */
	public boolean missionary(Long idProfil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge star
	 * @param idProfil
	 */
	public boolean star(Long idProfil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge journalist
	 * @param idProfil
	 */
	public boolean journalist(Long idProfil){
		return false;
	}
	
	/**
	 * Verifier si le profil peut obtenir le badge walker
	 * @param idProfil
	 */
	public boolean walker(Long idProfil){
		return false;
	}
	
	/**
	 * verifier si la team peu augmenter de niveau
	 * @param idTeam
	 */
	public boolean teamLevel(Long idTeam){
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