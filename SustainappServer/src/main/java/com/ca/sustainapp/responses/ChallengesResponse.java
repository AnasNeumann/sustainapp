package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour la reception de tous les challenges en infinity scroll
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
public class ChallengesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<ChallengeEntity> challenges = new SustainappList<ChallengeEntity>();
	
	/**
	 * @return the challenges
	 */
	public List<ChallengeEntity> getChallenges() {
		return challenges;
	}
	
	/**
	 * @param challenges the challenges to set
	 */
	public ChallengesResponse setChallenges(List<ChallengeEntity> challenges) {
		this.challenges = challenges;
		return this;
	}

}