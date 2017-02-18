package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse d'un ou plusieurs teams
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
public class TeamsResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<TeamEntity> teams = new SustainappList<TeamEntity>();
	
	/**
	 * @return the teams
	 */
	public List<TeamEntity> getTeams() {
		return teams;
	}
	
	/**
	 * @param teams the teams to set
	 */
	public TeamsResponse setTeams(List<TeamEntity> teams) {
		this.teams = teams;
		return this;
	}
	
}
