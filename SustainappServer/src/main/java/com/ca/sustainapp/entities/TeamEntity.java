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
 * TEAM table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 29/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "TEAM")
@SequenceGenerator(name = "team_id_seq_generator", sequenceName = "team_id_seq")
@Cacheable(false)
public class TeamEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "LEVEL")
	private Integer level;
	
	@Column(name = "AVATAR")
	private byte[] avatar;
	
	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "teamId", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	private List<TeamRoleEntity> listRole = new ArrayList<TeamRoleEntity>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public TeamEntity setId(Long id) {
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
	public TeamEntity setName(String name) {
		this.name = name;
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
	public TeamEntity setLevel(Integer level) {
		this.level = level;
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
	public TeamEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the listRole
	 */
	public List<TeamRoleEntity> getListRole() {
		return listRole;
	}

	/**
	 * @param listRole the listRole to set
	 */
	public TeamEntity setListRole(List<TeamRoleEntity> listRole) {
		this.listRole = listRole;
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
	public TeamEntity setAvatar(byte[] avatar) {
		this.avatar = avatar;
		return this;
	}
	
}