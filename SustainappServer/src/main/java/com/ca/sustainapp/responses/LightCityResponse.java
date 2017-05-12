package com.ca.sustainapp.responses;

/**
 * Json de réponse pour l'affichage d'un light d'une ville
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/05/2017
 * @version 1.0
 */
public class LightCityResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String phone;
	private String mail;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public LightCityResponse setId(Long id) {
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
	public LightCityResponse setName(String name) {
		this.name = name;
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
	public LightCityResponse setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public LightCityResponse setMail(String mail) {
		this.mail = mail;
		return this;
	}
}