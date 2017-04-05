package com.ca.sustainapp.entities;

/**
 * Mother class for all numerotable entities
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 02/04/2017 
 */
public class GenericNumerotableEntity extends GenericEntity {
	protected Integer numero;

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public GenericNumerotableEntity setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}
}