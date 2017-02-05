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
 * TOPIC table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "TOPIC")
@SequenceGenerator(name = "topic_id_seq_generator", sequenceName = "topic_id_seq")
public class TopicEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "PICTURE")
	private byte[] picture;
	
	@Column(name = "LINK")
	private String link;
	
	@Column(name = "COURSE_ID")
	private Long curseId;
	
	@Column(name = "DIFFICULTY")
	private Integer difficulty;
	
	@Column(name = "CHILD_LEVEL")
	private Integer childLevel;
	
	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parentId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TopicEntity> listChild = new ArrayList<TopicEntity>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "topicId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<QuestionEntity> listQuestion = new ArrayList<QuestionEntity>();
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public TopicEntity setId(Long id) {
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
	public TopicEntity setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public TopicEntity setContent(String content) {
		this.content = content;
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
	public TopicEntity setPicture(byte[] picture) {
		this.picture = picture;
		return this;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public TopicEntity setLink(String link) {
		this.link = link;
		return this;
	}

	/**
	 * @return the curseId
	 */
	public Long getCurseId() {
		return curseId;
	}

	/**
	 * @param curseId the curseId to set
	 */
	public TopicEntity setCurseId(Long curseId) {
		this.curseId = curseId;
		return this;
	}

	/**
	 * @return the difficulty
	 */
	public Integer getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public TopicEntity setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	/**
	 * @return the childLevel
	 */
	public Integer getChildLevel() {
		return childLevel;
	}

	/**
	 * @param childLevel the childLevel to set
	 */
	public TopicEntity setChildLevel(Integer childLevel) {
		this.childLevel = childLevel;
		return this;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public TopicEntity setParentId(Long parentId) {
		this.parentId = parentId;
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
	public TopicEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the listChild
	 */
	public List<TopicEntity> getListChild() {
		return listChild;
	}

	/**
	 * @param listChild the listChild to set
	 */
	public TopicEntity setListChild(List<TopicEntity> listChild) {
		this.listChild = listChild;
		return this;
	}

	/**
	 * @return the listQuestion
	 */
	public List<QuestionEntity> getListQuestion() {
		return listQuestion;
	}

	/**
	 * @param listQuestion the listQuestion to set
	 */
	public TopicEntity setListQuestion(List<QuestionEntity> listQuestion) {
		this.listQuestion = listQuestion;
		return this;
	}
}