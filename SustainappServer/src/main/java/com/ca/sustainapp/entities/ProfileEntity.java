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
import org.springframework.data.annotation.Transient;

/**
 * PROFILE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "PROFILE")
@SequenceGenerator(name = "profile_id_seq_generator", sequenceName = "profile_id_seq")
public class ProfileEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_seq_generator")
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
	
	@Column(name = "LEVEL")
	private Integer level;
	
	@Transient
	private transient String base64Avatar;
	
	@Column(name = "COVER")
	private byte[] cover;
	
	@Transient
	private transient String base64Cover;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@Column(name = "USER_ACCOUNT_ID")
	private Long userId;
	
	/**
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "creatorId", cascade = CascadeType.ALL, orphanRemoval = true)
		@Fetch(FetchMode.SELECT)
		private List<CourseEntity> listCourse = new ArrayList<CourseEntity>();
	**/
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TopicValidationEntity> listValidation = new ArrayList<TopicValidationEntity>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TeamRoleEntity> listTeam = new ArrayList<TeamRoleEntity>();

	/**
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "creatorId", cascade = CascadeType.ALL, orphanRemoval = true)
		@Fetch(FetchMode.SELECT)
		private List<ChallengeEntity> listChallenge = new ArrayList<ChallengeEntity>();
	**/
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<ProfilBadgeEntity> listBadge = new ArrayList<ProfilBadgeEntity>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<ReadNewsEntity> listNews = new ArrayList<ReadNewsEntity>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TravelEntity> listTravel = new ArrayList<TravelEntity>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<AwardEntity> listAward = new ArrayList<AwardEntity>();
	
	/**
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
		@Fetch(FetchMode.SELECT)
		private List<ReportEntity> listReport = new ArrayList<ReportEntity>();
	**/
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<NotificationEntity> listNotification = new ArrayList<NotificationEntity>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "creatorId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<MissionEntity> listOwnMission = new ArrayList<MissionEntity>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profilId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<MissionEntity> listMissionToDo = new ArrayList<MissionEntity>();
	
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

	/**
	 * @return the listValidation
	 */
	public List<TopicValidationEntity> getListValidation() {
		return listValidation;
	}

	/**
	 * @param listValidation the listValidation to set
	 */
	public ProfileEntity setListValidation(List<TopicValidationEntity> listValidation) {
		this.listValidation = listValidation;
		return this;
	}

	/**
	 * @return the listTeam
	 */
	public List<TeamRoleEntity> getListTeam() {
		return listTeam;
	}

	/**
	 * @param listTeam the listTeam to set
	 */
	public ProfileEntity setListTeam(List<TeamRoleEntity> listTeam) {
		this.listTeam = listTeam;
		return this;
	}

	/**
	 * @return the listBadge
	 */
	public List<ProfilBadgeEntity> getListBadge() {
		return listBadge;
	}

	/**
	 * @param listBadge the listBadge to set
	 */
	public ProfileEntity setListBadge(List<ProfilBadgeEntity> listBadge) {
		this.listBadge = listBadge;
		return this;
	}

	/**
	 * @return the listNews
	 */
	public List<ReadNewsEntity> getListNews() {
		return listNews;
	}

	/**
	 * @param listNews the listNews to set
	 */
	public ProfileEntity setListNews(List<ReadNewsEntity> listNews) {
		this.listNews = listNews;
		return this;
	}

	/**
	 * @return the listTravel
	 */
	public List<TravelEntity> getListTravel() {
		return listTravel;
	}

	/**
	 * @param listTravel the listTravel to set
	 */
	public ProfileEntity setListTravel(List<TravelEntity> listTravel) {
		this.listTravel = listTravel;
		return this;
	}

	/**
	 * @return the listAward
	 */
	public List<AwardEntity> getListAward() {
		return listAward;
	}

	/**
	 * @param listAward the listAward to set
	 */
	public ProfileEntity setListAward(List<AwardEntity> listAward) {
		this.listAward = listAward;
		return this;
	}

	/**
	 * @return the listNotification
	 */
	public List<NotificationEntity> getListNotification() {
		return listNotification;
	}

	/**
	 * @param listNotification the listNotification to set
	 */
	public ProfileEntity setListNotification(List<NotificationEntity> listNotification) {
		this.listNotification = listNotification;
		return this;
	}

	/**
	 * @return the listOwnMission
	 */
	public List<MissionEntity> getListOwnMission() {
		return listOwnMission;
	}

	/**
	 * @param listOwnMission the listOwnMission to set
	 */
	public ProfileEntity setListOwnMission(List<MissionEntity> listOwnMission) {
		this.listOwnMission = listOwnMission;
		return this;
	}

	/**
	 * @return the listMissionToDo
	 */
	public List<MissionEntity> getListMissionToDo() {
		return listMissionToDo;
	}

	/**
	 * @param listMissionToDo the listMissionToDo to set
	 */
	public ProfileEntity setListMissionToDo(List<MissionEntity> listMissionToDo) {
		this.listMissionToDo = listMissionToDo;
		return this;
	}

	/**
	 * @return the base64Avatar
	 */
	public String getBase64Avatar() {
		return base64Avatar;
	}

	/**
	 * @param base64Avatar the base64Avatar to set
	 */
	public ProfileEntity setBase64Avatar(String base64Avatar) {
		this.base64Avatar = base64Avatar;
		return this;
	}

	/**
	 * @return the base64Cover
	 */
	public String getBase64Cover() {
		return base64Cover;
	}

	/**
	 * @param base64Cover the base64Cover to set
	 */
	public ProfileEntity setBase64Cover(String base64Cover) {
		this.base64Cover = base64Cover;
		return this;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public ProfileEntity setUserId(Long userId) {
		this.userId = userId;
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
	public ProfileEntity setLevel(Integer level) {
		this.level = level;
		return this;
	}

}