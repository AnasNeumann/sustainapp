package com.ca.sustainapp.criteria;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
public class UserAccountCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mail;
	
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * @param mail the mail to set
	 */
	@QueryParam("mail")
	public UserAccountCriteria setMail(String mail) {
		this.mail = mail;
		return this;
	}
}