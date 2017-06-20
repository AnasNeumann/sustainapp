package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * USER_ACCOUNT table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
@Entity
@Table(name = "USER_ACCOUNT")
@SequenceGenerator(name = "user_account_id_seq_generator", sequenceName = "user_account_id_seq")
public class UserAccountEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MAIL")
	private String mail;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "IS_ADMIN")
	private Boolean isAdmin;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "USER_TYPE")
	private Integer type;
	
	@Column(name = "TOKEN_DELAY")
	private Calendar tokenDelay;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<ProfileEntity> profile = new ArrayList<ProfileEntity>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public UserAccountEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public UserAccountEntity setMail(String mail) {
		this.mail = mail;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public UserAccountEntity setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public UserAccountEntity setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public UserAccountEntity setToken(String token) {
		this.token = token;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps the timestamps to set
	 */
	public UserAccountEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the profile
	 */
	public ProfileEntity getProfile() {
		if(null == profile || profile.isEmpty()){
			return null;
		}
		return profile.get(0);
	}

	/**
	 * @param profile the profile to set
	 */
	public UserAccountEntity setProfile(ProfileEntity profile) {
		this.profile.clear();
		this.profile.add(profile);
		return this;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public UserAccountEntity setType(Integer type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the tokenDelay
	 */
	public Calendar getTokenDelay() {
		return tokenDelay;
	}

	/**
	 * @param tokenDelay the tokenDelay to set
	 */
	public UserAccountEntity setTokenDelay(Calendar tokenDelay) {
		this.tokenDelay = tokenDelay;
		return this;
	}

}