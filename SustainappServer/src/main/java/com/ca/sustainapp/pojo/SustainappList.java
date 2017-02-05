package com.ca.sustainapp.pojo;

import java.util.ArrayList;

public class SustainappList<E> extends ArrayList<E>{
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Add object into the list
	 * @param object
	 * @return
	 */
	public SustainappList<E> put(E object){
		this.add(object);
		return this;
	}
}