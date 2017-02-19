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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	private ChallengeTypeEntity challengType;
	
	@Column(name = "CREATOR_ID")
	private Long creatorId;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "challengeId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<ParticipationEntity> listParticipation = new ArrayList<ParticipationEntity>();

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
	public ChallengeTypeEntity getChallengType() {
		return challengType;
	}

	/**
	 * @param challengType the challengType to set
	 */
	public ChallengeEntity setChallengType(ChallengeTypeEntity challengType) {
		this.challengType = challengType;
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
	 * @return the listParticipation
	 */
	public List<ParticipationEntity> getListParticipation() {
		return listParticipation;
	}

	/**
	 * @param listParticipation the listParticipation to set
	 */
	public ChallengeEntity setListParticipation(List<ParticipationEntity> listParticipation) {
		this.listParticipation = listParticipation;
		return this;
	}

}