package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
public class ParticipationCriteria  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String about;
	private Long targetId;
	private String targetType;
	private Long challengeId;
	private Calendar timestamps;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	@QueryParam("id")
	public ParticipationCriteria setId(Long id) {
		this.id = id;
		return this;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	@QueryParam("title")
	public ParticipationCriteria setTitle(String title) {
		this.title = title;
		return this;
	}
	
	/**
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}
	
	/**
	 * @param about the about to set
	 */
	@QueryParam("about")
	public ParticipationCriteria setAbout(String about) {
		this.about = about;
		return this;
	}
	
	/**
	 * @return the targetId
	 */
	public Long getTargetId() {
		return targetId;
	}
	
	/**
	 * @param targetId the targetId to set
	 */
	@QueryParam("targetId")
	public ParticipationCriteria setTargetId(Long targetId) {
		this.targetId = targetId;
		return this;
	}
	
	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}
	
	/**
	 * @param targetType the targetType to set
	 */
	@QueryParam("targetType")
	public ParticipationCriteria setTargetType(String targetType) {
		this.targetType = targetType;
		return this;
	}
	
	/**
	 * @return the challengeId
	 */
	public Long getChallengeId() {
		return challengeId;
	}
	
	/**
	 * @param challengeId the challengeId to set
	 */
	@QueryParam("challengeId")
	public ParticipationCriteria setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
		return this;
	}
	
	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}
	
	/**
	 * @param timestamps the timestamps to set
	 */
	@QueryParam("timestamps")
	public ParticipationCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}