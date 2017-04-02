package com.ca.sustainapp.responses;

import java.util.Calendar;

import com.ca.sustainapp.entities.CourseEntity;

/**
 * Json de réponse light d'un cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
public class LightCourseResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String about;
	private byte[] picture;
	private Integer levelMin;
	private Calendar timestamps;
	private Integer open;
	
	/**
	 * Constructor for Courses
	 * @param cours
	 */
	public LightCourseResponse(CourseEntity cours){
		this.id = cours.getId();
		this.title = cours.getTitle();
		this.about = cours.getAbout();
		this.picture = cours.getPicture();
		this.levelMin = cours.getLevelMin();
		this.timestamps = cours.getTimestamps();
		this.open = cours.getOpen();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public LightCourseResponse setId(Long id) {
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
	public LightCourseResponse setTitle(String title) {
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
	public LightCourseResponse setAbout(String about) {
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
	public LightCourseResponse setPicture(byte[] picture) {
		this.picture = picture;
		return this;
	}
	
	/**
	 * @return the levelMin
	 */
	public Integer getLevelMin() {
		return levelMin;
	}
	
	/**
	 * @param levelMin the levelMin to set
	 */
	public LightCourseResponse setLevelMin(Integer levelMin) {
		this.levelMin = levelMin;
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
	public LightCourseResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the open
	 */
	public Integer getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public LightCourseResponse setOpen(Integer open) {
		this.open = open;
		return this;
	}
}