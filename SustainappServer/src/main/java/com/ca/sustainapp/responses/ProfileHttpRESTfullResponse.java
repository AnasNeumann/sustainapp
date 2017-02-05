package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Class response for profile webservice
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/01/017
 * @version 1.0
 */
public class ProfileHttpRESTfullResponse extends HttpRESTfullResponse {

	private static final long serialVersionUID = 1L;
	private List<ProfileEntity> data = new SustainappList<ProfileEntity>();
	
	/**
	 * @return the data
	 */
	public List<ProfileEntity> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public ProfileHttpRESTfullResponse setData(List<ProfileEntity> data) {
		this.data = data;
		return this;
	}
}