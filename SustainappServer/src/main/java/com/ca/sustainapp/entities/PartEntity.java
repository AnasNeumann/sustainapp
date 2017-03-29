package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * PART table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
@Entity
@Table(name = "PART")
@SequenceGenerator(name = "part_id_seq_generator", sequenceName = "part_id_seq")
public class PartEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CONTENT")
	private String content;

	@Column(name = "DOCUMENT")
	private byte[] document;
	
	@Column(name = "PART_TYPE")
	private Integer type;
	
	@Column(name = "TOPIC_ID")
	private Long topicId;
	
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
	public PartEntity setId(Long id) {
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
	public PartEntity setTitle(String title) {
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
	public PartEntity setContent(String content) {
		this.content = content;
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
	public PartEntity setDocument(byte[] document) {
		this.document = document;
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
	public PartEntity setType(Integer type) {
		this.type = type;
		return this;
	}

	/**
	 * @return the topicId
	 */
	public Long getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public PartEntity setTopicId(Long topicId) {
		this.topicId = topicId;
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
	public PartEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}