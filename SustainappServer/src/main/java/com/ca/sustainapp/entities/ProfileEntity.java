package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PROFILE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "PROFILE")
public class ProfileEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "BORN_DATE")
	private Calendar bornDate;
	
	@Column(name = "AVATAR")
	private byte[] avatar;
	
	@Column(name = "COVER")
	private byte[] cover;
	
	@Column(name = "IS_ADMIN")
	private Boolean isAdmin;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public ProfileEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public ProfileEntity setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public ProfileEntity setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the bornDate
	 */
	public Calendar getBornDate() {
		return bornDate;
	}

	/**
	 * @param bornDate the bornDate to set
	 */
	public ProfileEntity setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
		return this;
	}

	/**
	 * @return the avatar
	 */
	public byte[] getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public ProfileEntity setAvatar(byte[] avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * @return the cover
	 */
	public byte[] getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public ProfileEntity setCover(byte[] cover) {
		this.cover = cover;
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
	public ProfileEntity setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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
	public ProfileEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}