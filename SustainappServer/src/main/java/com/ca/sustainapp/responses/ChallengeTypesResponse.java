package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ChallengeTypeEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour la reception de tous les types de challenges
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/03/2017
 * @version 1.0
 */
public class ChallengeTypesResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<ChallengeTypeEntity> types = new SustainappList<ChallengeTypeEntity>();
	
	/**
	 * @return the types
	 */
	public List<ChallengeTypeEntity> getTypes() {
		return types;
	}
	
	/**
	 * @param types the types to set
	 */
	public ChallengeTypesResponse setTypes(List<ChallengeTypeEntity> types) {
		this.types = types;
		return this;
	}

}