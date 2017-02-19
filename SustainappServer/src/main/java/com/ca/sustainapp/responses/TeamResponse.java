package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ParticipationEntity;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage d'une equipe
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
public class TeamResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private TeamEntity team;
	private List<ProfileEntity> members = new SustainappList<ProfileEntity>();
	private List<ProfileEntity> requests = new SustainappList<ProfileEntity>();
	private ProfileEntity owner;
	private List<ParticipationEntity> participations = new SustainappList<ParticipationEntity>();
	private String role;

	/**
	 * @return the team
	 */
	public TeamEntity getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public TeamResponse setTeam(TeamEntity team) {
		this.team = team;
		return this;
	}

	/**
	 * @return the members
	 */
	public List<ProfileEntity> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public TeamResponse setMembers(List<ProfileEntity> members) {
		this.members = members;
		return this;
	}

	/**
	 * @return the requests
	 */
	public List<ProfileEntity> getRequests() {
		return requests;
	}

	/**
	 * @param requests
	 *            the requests to set
	 */
	public TeamResponse setRequests(List<ProfileEntity> requests) {
		this.requests = requests;
		return this;
	}

	/**
	 * @return the owner
	 */
	public ProfileEntity getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public TeamResponse setOwner(ProfileEntity owner) {
		this.owner = owner;
		return this;
	}

	/**
	 * @return the participations
	 */
	public List<ParticipationEntity> getParticipations() {
		return participations;
	}

	/**
	 * @param participations
	 *            the participations to set
	 */
	public TeamResponse setParticipations(List<ParticipationEntity> participations) {
		this.participations = participations;
		return this;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public TeamResponse setRole(String role) {
		this.role = role;
		return this;
	}

}