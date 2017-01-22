package com.ca.sustainapp.boot;

/**
 * Classe contenant toutes les constantes utilisées dans SustainAppServer
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 22/01/2017
 * @version 1.0
 */
public class SustainappConstantes {
	
	/**
	 * Les types d'URLs
	 */
	public final static String POST = "post";
	public final static String GET = "get";
	public final static String DELETE = "delete";
	public final static String PUT = "put";
	
	/**
	 * Charsets
	 */
	public final static String ENCODING_UTF8 = "UTF-8";
	public final static String ENCODING_ISO_8859_1 = "ISO-8859-1";

	/**
	 * Mime-types
	 */
	public final static String MIME_HTML = "text/html";
	public final static String MIME_JSON_UTF8 = "application/json;charset=UTF-8";
	public final static String MIME_JSON = "application/json";

	/**
	 * Autres
	 */
	public final static String EMPTY_STRING = "";
	public final static String SEPARATOR_DEFAULT = ";";
	public final static String EMPTY_JSON = "[]";
	public final static String SUCCES_JSON = "{\"result\":\"success\"}";
	public final static String BLANK_SEPARATOR = "\\s+";
	public final static String COMMA_SEPARATOR = "\\,";
}
