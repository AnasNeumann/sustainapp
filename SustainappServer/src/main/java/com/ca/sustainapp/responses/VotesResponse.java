package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse des votes d'une participation
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/03/2017
 * @version 1.0
 */
public class VotesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<VoteResponse> votes = new SustainappList<VoteResponse>();
	
	/**
	 * @return the votes
	 */
	public List<VoteResponse> getVotes() {
		return votes;
	}
	
	/**
	 * @param votes the votes to set
	 */
	public VotesResponse setVotes(List<VoteResponse> votes) {
		this.votes = votes;
		return this;
	}
	
}