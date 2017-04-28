package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Json de réponse pour l'affichage du journal
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
public class NewspaperResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<LeaderCoursResponse> cours = new ArrayList<LeaderCoursResponse>();
	private List<LeaderParticipationResponse> participations = new ArrayList<LeaderParticipationResponse>();

	/**
	 * @return the cours
	 */
	public List<LeaderCoursResponse> getCours() {
		return cours;
	}

	/**
	 * @param cours the cours to set
	 */
	public NewspaperResponse setCours(List<LeaderCoursResponse> cours) {
		this.cours = cours;
		return this;
	}

	/**
	 * @return the participations
	 */
	public List<LeaderParticipationResponse> getParticipations() {
		return participations;
	}

	/**
	 * @param participations the participations to set
	 */
	public NewspaperResponse setParticipations(List<LeaderParticipationResponse> participations) {
		this.participations = participations;
		return this;
	}
}