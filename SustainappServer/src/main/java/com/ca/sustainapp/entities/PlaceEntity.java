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
 * PLACE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Entity
@Table(name = "PLACE")
@SequenceGenerator(name = "place_id_seq_generator", sequenceName = "place_id_seq")
public class PlaceEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "LONGITUDE")
	private Integer longitude;
	
	@Column(name = "LATITUDE")
	private Integer latitude;
	
	@Column(name = "CITY_ID")
	private Long cityId;
	
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
	public PlaceEntity setId(Long id) {
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
	public PlaceEntity setName(String name) {
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
	public PlaceEntity setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public PlaceEntity setAddress(String address) {
		this.address = address;
		return this;
	}

	/**
	 * @return the longitude
	 */
	public Integer getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public PlaceEntity setLongitude(Integer longitude) {
		this.longitude = longitude;
		return this;
	}

	/**
	 * @return the latitude
	 */
	public Integer getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public PlaceEntity setLatitude(Integer latitude) {
		this.latitude = latitude;
		return this;
	}

	/**
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public PlaceEntity setCityId(Long cityId) {
		this.cityId = cityId;
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
	public PlaceEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}
