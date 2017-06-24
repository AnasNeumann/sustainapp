package com.ca.sustainapp.advice;

/**
 * Classe Exception de sécurité détectée
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/06/2017
 * @version
 */
public class SustainappSecurityException extends RuntimeException {

	/**
	 * ID
	 */
	private static final long serialVersionUID = 1L;

	 public SustainappSecurityException(String message) { 
		 super(message); 
     }
	
}
