package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.RankCourseEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage complet d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
public class CourseResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private CourseEntity cours;
	private Boolean isOwner;
	private LightProfileResponse owner;
	private Float averageRank;
	private RankCourseEntity rank;
	private List<LightTopicResponse> topics = new SustainappList<LightTopicResponse>();
	
	/**
	 * @return the cours
	 */
	public CourseEntity getCours() {
		return cours;
	}

	/**
	 * @param cours the cours to set
	 */
	public CourseResponse setCours(CourseEntity cours) {
		this.cours = cours;
		return this;
	}

	/**
	 * @return the isOwner
	 */
	public Boolean getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner
	 *            the isOwner to set
	 */
	public CourseResponse setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
		return this;
	}

	/**
	 * @return the owner
	 */
	public LightProfileResponse getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public CourseResponse setOwner(LightProfileResponse owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * @return the averageRank
	 */
	public Float getAverageRank() {
		return averageRank;
	}

	/**
	 * @param averageRank
	 *            the averageRank to set
	 */
	public CourseResponse setAverageRank(Float averageRank) {
		this.averageRank = averageRank;
		return this;
	}

	/**
	 * @return the rank
	 */
	public RankCourseEntity getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public CourseResponse setRank(RankCourseEntity rank) {
		this.rank = rank;
		return this;
	}

	/**
	 * @return the topics
	 */
	public List<LightTopicResponse> getTopics() {
		return topics;
	}

	/**
	 * @param topics
	 *            the topics to set
	 */
	public CourseResponse setTopics(List<LightTopicResponse> topics) {
		this.topics = topics;
		return this;
	}
}