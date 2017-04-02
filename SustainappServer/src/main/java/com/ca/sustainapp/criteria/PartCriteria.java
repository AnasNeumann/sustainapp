package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/03/2017
 * @version 1.0
 */
public class PartCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String content;
	private Integer type;
	private Long topicId;
	private Integer numero;
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
	@QueryParam("id")
	public PartCriteria setId(Long id) {
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
	@QueryParam("title")
	public PartCriteria setTitle(String title) {
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
	@QueryParam("content")
	public PartCriteria setContent(String content) {
		this.content = content;
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
	@QueryParam("type")
	public PartCriteria setType(Integer type) {
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
	@QueryParam("topicId")
	public PartCriteria setTopicId(Long topicId) {
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
	@QueryParam("timestamps")
	public PartCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	@QueryParam("numero")
	public PartCriteria setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}
}