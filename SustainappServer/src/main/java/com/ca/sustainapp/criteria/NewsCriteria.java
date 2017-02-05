package com.ca.sustainapp.criteria;

import java.io.Serializable;
import java.util.Calendar;

import javax.ws.rs.QueryParam;

/**
 * Criteria for research
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class NewsCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String content;
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
	public NewsCriteria setId(Long id) {
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
	public NewsCriteria setTitle(String title) {
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
	public NewsCriteria setContent(String content) {
		this.content = content;
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
	public NewsCriteria setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}