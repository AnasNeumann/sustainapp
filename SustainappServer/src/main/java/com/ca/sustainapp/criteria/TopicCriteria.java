package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class TopicCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String content;
	private Long curseId;
	private Integer numero;
	private Calendar timestamps;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@QueryParam("id")
	public TopicCriteria setId(Long id) {
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
	 * @param title
	 *            the title to set
	 */
	@QueryParam("title")
	public TopicCriteria setTitle(String title) {
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
	 * @param content
	 *            the content to set
	 */
	@QueryParam("content")
	public TopicCriteria setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * @return the curseId
	 */
	public Long getCurseId() {
		return curseId;
	}

	/**
	 * @param curseId
	 *            the curseId to set
	 */
	@QueryParam("curseId")
	public TopicCriteria setCurseId(Long curseId) {
		this.curseId = curseId;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	@QueryParam("timestamps")
	public TopicCriteria setTimestamps(Calendar timestamps) {
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
	public TopicCriteria setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

}