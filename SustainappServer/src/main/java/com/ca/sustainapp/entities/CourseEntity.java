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
import org.springframework.data.annotation.Transient;

/**
 * COURSE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "COURSE")
@SequenceGenerator(name = "course_id_seq_generator", sequenceName = "course_id_seq")
public class CourseEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "PICTURE")
	private byte[] picture;
	
	@Transient
	private transient String base64;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
	private CategoryEntity category;
	
	@Column(name = "CREATOR_ID")
	private Long creatorId;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "courseId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<RankCourseEntity> listRank = new ArrayList<RankCourseEntity>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "curseId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TopicEntity> listTopic = new ArrayList<TopicEntity>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public CourseEntity setId(Long id) {
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
	public CourseEntity setTitle(String title) {
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
	public CourseEntity setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the picture
	 */
	public byte[] getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public CourseEntity setPicture(byte[] picture) {
		this.picture = picture;
		return this;
	}

	/**
	 * @return the category
	 */
	public CategoryEntity getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public CourseEntity setCategory(CategoryEntity category) {
		this.category = category;
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
	public CourseEntity setCreatorId(Long creatorId) {
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
	public CourseEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the listRank
	 */
	public List<RankCourseEntity> getListRank() {
		return listRank;
	}

	/**
	 * @param listRank the listRank to set
	 */
	public CourseEntity setListRank(List<RankCourseEntity> listRank) {
		this.listRank = listRank;
		return this;
	}

	/**
	 * @return the listTopic
	 */
	public List<TopicEntity> getListTopic() {
		return listTopic;
	}

	/**
	 * @param listTopic the listTopic to set
	 */
	public CourseEntity setListTopic(List<TopicEntity> listTopic) {
		this.listTopic = listTopic;
		return this;
	}
	
	/**
	 * @return the base64
	 */
	public String getBase64() {
		return base64;
	}

	/**
	 * @param base64 the base64 to set
	 */
	public CourseEntity setBase64(String base64) {
		this.base64 = base64;
		return this;
	}
}