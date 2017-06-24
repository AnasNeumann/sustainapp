package com.ca.sustainapp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
 * CITY table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Entity
@Table(name = "CITY")
@SequenceGenerator(name = "city_id_seq_generator", sequenceName = "city_id_seq")
@Cacheable(false)
public class CityEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ABOUT")
	private String about;
	
	@Column(name = "ACTIF")
	private Integer actif;
	
	@Column(name = "USER_ACCOUNT_ID")
	private Long userId;
	
	@Column(name = "COVER")
	private byte[] cover;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "WEBSITE")
	private String website;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cityId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<PlaceEntity> places = new ArrayList<PlaceEntity>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public CityEntity setId(Long id) {
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
	public CityEntity setName(String name) {
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
	public CityEntity setAbout(String about) {
		this.about = about;
		return this;
	}

	/**
	 * @return the actif
	 */
	public Integer getActif() {
		return actif;
	}

	/**
	 * @param actif the actif to set
	 */
	public CityEntity setActif(Integer actif) {
		this.actif = actif;
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
	public CityEntity setUserId(Long userId) {
		this.userId = userId;
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
	public CityEntity setCover(byte[] cover) {
		this.cover = cover;
		return this;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public CityEntity setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public CityEntity setWebsite(String website) {
		this.website = website;
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
	public CityEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the places
	 */
	public List<PlaceEntity> getPlaces() {
		return places;
	}

	/**
	 * @param places the places to set
	 */
	public CityEntity setPlaces(List<PlaceEntity> places) {
		this.places = places;
		return this;
	}
}
