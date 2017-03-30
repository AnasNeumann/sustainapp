package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'un profil complet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
public class ProfileResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private ProfileEntity profile;
	private List<BadgeEntity> badges = new SustainappList<BadgeEntity>();
	private List<LightCourseResponse> courses = new SustainappList<LightCourseResponse>();
	
	/**
	 * @return the profile
	 */
	public ProfileEntity getProfile() {
		return profile;
	}
	
	/**
	 * @param profile the profile to set
	 */
	public ProfileResponse setProfile(ProfileEntity profile) {
		this.profile = profile;
		return this;
	}
	
	/**
	 * @return the badges
	 */
	public List<BadgeEntity> getBadges() {
		return badges;
	}
	
	/**
	 * @param badges the badges to set
	 */
	public ProfileResponse setBadges(List<BadgeEntity> badges) {
		this.badges = badges;
		return this;
	}
	
	/**
	 * @return the courses
	 */
	public List<LightCourseResponse> getCourses() {
		return courses;
	}
	
	/**
	 * @param courses the courses to set
	 */
	public ProfileResponse setCourses(List<LightCourseResponse> courses) {
		this.courses = courses;
		return this;
	}
}