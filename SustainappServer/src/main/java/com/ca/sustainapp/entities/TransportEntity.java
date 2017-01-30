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
 * TRANSPORT table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "TRANSPORT")
@SequenceGenerator(name = "transport_id_seq_generator", sequenceName = "transport_id_seq")
public class TransportEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transport_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ENERGICAL_VALUE")
	private Integer energicalValue;
	
	@Column(name = "ICON")
	private byte[] icon;
	
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
	public TransportEntity setId(Long id) {
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
	public TransportEntity setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the energicalValue
	 */
	public Integer getEnergicalValue() {
		return energicalValue;
	}

	/**
	 * @param energicalValue the energicalValue to set
	 */
	public TransportEntity setEnergicalValue(Integer energicalValue) {
		this.energicalValue = energicalValue;
		return this;
	}

	/**
	 * @return the icon
	 */
	public byte[] getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public TransportEntity setIcon(byte[] icon) {
		this.icon = icon;
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
	public TransportEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}