package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * PROFILE_BADGE table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "PROFILE_BADGE")
@SequenceGenerator(name = "profile_badge_id_seq_generator", sequenceName = "profile_badge_id_seq")
public class ProfilBadgeEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_badge_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PROFILE_ID")
	private Long profilId;
	
	@ManyToOne
	@JoinColumn(name = "BADGE_ID", referencedColumnName = "ID")
	private BadgeEntity badge;
	
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
	public ProfilBadgeEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the profilId
	 */
	public Long getProfilId() {
		return profilId;
	}

	/**
	 * @param profilId the profilId to set
	 */
	public ProfilBadgeEntity setProfilId(Long profilId) {
		this.profilId = profilId;
		return this;
	}

	/**
	 * @return the badge
	 */
	public BadgeEntity getBadge() {
		return badge;
	}

	/**
	 * @param badge the badge to set
	 */
	public ProfilBadgeEntity setBadge(BadgeEntity badge) {
		this.badge = badge;
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
	public ProfilBadgeEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}