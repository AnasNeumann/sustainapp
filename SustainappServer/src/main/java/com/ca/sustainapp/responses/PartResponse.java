package com.ca.sustainapp.responses;

/**
 * Json de réponse d'une nouvelle partie de cours
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/04/2017
 * @version 1.0
 */
public class PartResponse extends IdResponse{
	private static final long serialVersionUID = 1L;
	private String Content;
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}
	
	/**
	 * @param content the content to set
	 */
	public PartResponse setContent(String content) {
		Content = content;
		return this;
	}
}