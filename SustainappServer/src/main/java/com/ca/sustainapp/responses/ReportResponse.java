package com.ca.sustainapp.responses;

import java.util.Calendar;

import com.ca.sustainapp.entities.ReportEntity;

/**
 * Json de réponse d'un signalement
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/05/2017
 * @version 1.0
 */
public class ReportResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;	
	private String message;
	private byte[] document;
	private String documentType;
	private Integer state;
	private Calendar timestamps;
	private LightProfileResponse owner;
	
	/**
	 * Constructor from entity
	 * @param entity
	 */
	public ReportResponse(ReportEntity entity){
		this.id = entity.getId();	
		this.message = entity.getMessage();
		this.document = entity.getDocument();
		this.documentType = entity.getDocumentType();
		this.state = entity.getState();
		this.timestamps = entity.getTimestamps();
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
	public ReportResponse setId(Long id) {
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
	public ReportResponse setMessage(String message) {
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
	public ReportResponse setDocument(byte[] document) {
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
	public ReportResponse setDocumentType(String documentType) {
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
	public ReportResponse setState(Integer state) {
		this.state = state;
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
	public ReportResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the owner
	 */
	public LightProfileResponse getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public ReportResponse setOwner(LightProfileResponse owner) {
		this.owner = owner;
		return this;
	}
}