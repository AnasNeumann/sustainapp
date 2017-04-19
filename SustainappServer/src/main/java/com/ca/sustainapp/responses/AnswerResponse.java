package com.ca.sustainapp.responses;

import java.util.Calendar;

import com.ca.sustainapp.entities.AnswerEntity;

/**
 * Json de réponse pour l'affichage d'une réponse
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 13/04/2017
 * @version 1.0
 */
public class AnswerResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String message;
	private Integer numero;
	private byte[] picture;
	private Calendar timestamps;
	private String data;
	
	/**
	 * Constructeur à partir de l'entity
	 * @param entity
	 */
	public AnswerResponse(AnswerEntity entity){
		this.id = entity.getId();
		this.message = entity.getMessage();
		this.numero = entity.getNumero();
		this.picture = entity.getPicture();
		this.timestamps = entity.getTimestamps();
		this.data = null;
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
	public AnswerResponse setId(Long id) {
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
	public AnswerResponse setMessage(String message) {
		this.message = message;
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
	public AnswerResponse setNumero(Integer numero) {
		this.numero = numero;
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
	public AnswerResponse setPicture(byte[] picture) {
		this.picture = picture;
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
	public AnswerResponse setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public AnswerResponse setData(String data) {
		this.data = data;
		return this;
	}
	
}