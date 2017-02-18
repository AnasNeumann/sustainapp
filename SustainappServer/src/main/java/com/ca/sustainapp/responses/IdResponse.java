package com.ca.sustainapp.responses;

/**
 * Json de réponse d'un id de manière générale
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/02/2017
 * @version 1.0
 */
public class IdResponse  extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public IdResponse setId(Long id) {
		this.id = id;
		return this;
	}
}
