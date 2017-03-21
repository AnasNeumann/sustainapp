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
 * PARTICIPATION table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
@Entity
@Table(name = "PARTICIPATION")
@SequenceGenerator(name = "participation_id_seq_generator", sequenceName = "participation_id_seq")
public class ParticipationEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participation_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "DOCUMENT")
	private byte[] document;
	
	@Column(name = "TARGET_ID")
	private Long targetId;
	
	@Column(name = "TARGET_TYPE")
	private String targetType;
	
	@Column(name = "CHALLENGE_ID")
	private Long challengeId;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "participationId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<ChallengeVoteEntity> votes = new ArrayList<ChallengeVoteEntity>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public ParticipationEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public ParticipationEntity setTitle(String title) {
		this.title = title;
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
	public ParticipationEntity setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the document
	 */
	public byte[] getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public ParticipationEntity setDocument(byte[] document) {
		this.document = document;
		return this;
	}

	/**
	 * @return the targetId
	 */
	public Long getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public ParticipationEntity setTargetId(Long targetId) {
		this.targetId = targetId;
		return this;
	}

	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * @param targetType the targetType to set
	 */
	public ParticipationEntity setTargetType(String targetType) {
		this.targetType = targetType;
		return this;
	}

	/**
	 * @return the challengeId
	 */
	public Long getChallengeId() {
		return challengeId;
	}

	/**
	 * @param challengeId the challengeId to set
	 */
	public ParticipationEntity setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
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
	public ParticipationEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the lisVote
	 */
	public List<ChallengeVoteEntity> getVotes() {
		return votes;
	}

	/**
	 * @param lisVote the lisVote to set
	 */
	public ParticipationEntity setVotes(List<ChallengeVoteEntity> votes) {
		this.votes = votes;
		return this;
	}	
}