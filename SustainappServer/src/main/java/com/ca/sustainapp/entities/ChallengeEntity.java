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
 * CHALLENGE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "CHALLENGE")
@SequenceGenerator(name = "challenge_id_seq_generator", sequenceName = "challenge_id_seq")
public class ChallengeEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "END_DATE")
	private Calendar endDate;
	
	@Column(name = "ICON")
	private byte[] icon;
	
	@Column(name = "TEAM_ENABLED")
	private Boolean teamEnabled;
	
	@ManyToOne
	@JoinColumn(name = "CHALLENGE_TYPE_ID", referencedColumnName = "ID")
	private ChallengeTypeEntity challengeType;
	
	@Column(name = "CREATOR_ID")
	private Long creatorId;

	@Column(name = "MIN_LEVEL")
	private Integer minLevel;
	
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
	public ChallengeEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public ChallengeEntity setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * @param about the about to set
	 */
	public ChallengeEntity setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public ChallengeEntity setEndDate(Calendar endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * @return the icon
	 */
	public byte[] getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public ChallengeEntity setIcon(byte[] icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * @return the teamEnabled
	 */
	public Boolean getTeamEnabled() {
		return teamEnabled;
	}

	/**
	 * @param teamEnabled the teamEnabled to set
	 */
	public ChallengeEntity setTeamEnabled(Boolean teamEnabled) {
		this.teamEnabled = teamEnabled;
		return this;
	}

	/**
	 * @return the challengType
	 */
	public ChallengeTypeEntity getChallengeType() {
		return challengeType;
	}

	/**
	 * @param challengType the challengType to set
	 */
	public ChallengeEntity setChallengeType(ChallengeTypeEntity challengeType) {
		this.challengeType = challengeType;
		return this;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public ChallengeEntity setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
	public ChallengeEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the minLevel
	 */
	public Integer getMinLevel() {
		return minLevel;
	}

	/**
	 * @param minLevel the minLevel to set
	 */
	public ChallengeEntity setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
		return this;
	}
}