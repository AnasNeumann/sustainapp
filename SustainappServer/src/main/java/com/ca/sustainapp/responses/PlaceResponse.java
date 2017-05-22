package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.List;

import com.ca.sustainapp.entities.PlaceEntity;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.entities.PlacePictureEntity;

/**
 * Json de réponse pour l'affichage complet d'un éco-lieu
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 15/05/2017
 * @version 1.0
 */
public class PlaceResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private PlaceEntity place;
	private PlaceNoteEntity note;
	private Float average;
	private Boolean isOwner;
	private List<PlacePictureEntity> pictures = new ArrayList<PlacePictureEntity>();
	private Integer nbrNotes;

	/**
	 * @return the place
	 */
	public PlaceEntity getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public PlaceResponse setPlace(PlaceEntity place) {
		this.place = place;
		return this;
	}

	/**
	 * @return the note
	 */
	public PlaceNoteEntity getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public PlaceResponse setNote(PlaceNoteEntity note) {
		this.note = note;
		return this;
	}

	/**
	 * @return the average
	 */
	public Float getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public PlaceResponse setAverage(Float average) {
		this.average = average;
		return this;
	}

	/**
	 * @return the isOwner
	 */
	public Boolean getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner the isOwner to set
	 */
	public PlaceResponse setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
		return this;
	}

	/**
	 * @return the pictures
	 */
	public List<PlacePictureEntity> getPictures() {
		return pictures;
	}

	/**
	 * @param pictures the pictures to set
	 */
	public PlaceResponse setPictures(List<PlacePictureEntity> pictures) {
		this.pictures = pictures;
		return this;
	}

	/**
	 * @return the nbrNotes
	 */
	public Integer getNbrNotes() {
		return nbrNotes;
	}

	/**
	 * @param nbrNotes the nbrNotes to set
	 */
	public PlaceResponse setNbrNotes(Integer nbrNotes) {
		this.nbrNotes = nbrNotes;
		return this;
	}
}