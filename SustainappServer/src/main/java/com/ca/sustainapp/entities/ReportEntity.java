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
 * REPORT table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Entity
@Table(name = "REPORT")
@SequenceGenerator(name = "report_id_seq_generator", sequenceName = "report_id_seq")
public class ReportEntity extends GenericEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "DOCUMENT")
	private byte[] document;
	
	@Column(name = "DOCUMENT_TYPE")
	private String documentType;
	
	@Column(name = "STATE")
	private Integer state;
	
	@Column(name = "PROFILE_ID")
	private Long profilId;
	
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
	public ReportEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public ReportEntity setMessage(String message) {
		this.message = message;
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
	public ReportEntity setDocument(byte[] document) {
		this.document = document;
		return this;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public ReportEntity setDocumentType(String documentType) {
		this.documentType = documentType;
		return this;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public ReportEntity setState(Integer state) {
		this.state = state;
		return this;
	}

	/**
	 * @return the profilId
	 */
	public Long getProfilId() {
		return profilId;
	}

	/**
	 * @param profilId the profilId to set
	 */
	public ReportEntity setProfilId(Long profilId) {
		this.profilId = profilId;
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
	public ReportEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}