package com.ca.sustainapp.responses;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.TeamEntity;

/**
 * Json de réponse light d'un profile
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/03/2017
 * @version 1.0
 */
public class LightProfileResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String denomination;
	private byte[] avatar;
	private String type;
	private Integer level;
	
	/**
	 * Constructor for a complete profile
	 * @param profile
	 */
	public LightProfileResponse(ProfileEntity profile){
		if(null != profile){
			this.denomination = profile.getFirstName()+" "+profile.getLastName();
			this.id = profile.getId();
			this.avatar = profile.getAvatar();
			this.type = SustainappConstantes.TARGET_PROFILE;
			this.level = profile.getLevel();
		}	
	}
	
	/**
	 * Constructor for a team
	 * @param team
	 */
	public LightProfileResponse(TeamEntity team){
		if(null != team){
			this.denomination = team.getName();
			this.id = team.getId();
			this.avatar = team.getAvatar();
			this.type = SustainappConstantes.TARGET_TEAM;
			this.level = team.getLevel();
		}		
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public LightProfileResponse setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the denomination
	 */
	public String getDenomination() {
		return denomination;
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	public LightProfileResponse setDenomination(String denomination) {
		this.denomination = denomination;
		return this;
	}

	/**
	 * @return the avatar
	 */
	public byte[] getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public LightProfileResponse setAvatar(byte[] avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public LightProfileResponse setType(String type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
}