package com.ca.sustainapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Classe d'outils pour la gestion des dates
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class DateUtils {
	public static final String FORMAT_DATE = "dd/MM/yyyy";
	public static final String FORMAT_DATE_HOUR = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMAT_DATE_HOUR_MINUTE = "dd/MM/yyyy HH:mm";
	public static final String FORMAT_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String FORMAT_JOUR_DATE_HOUR = "EE dd/MM/yy HH:mm";
	public static final String FORMAT_AMERICAN = "yyyy-MM-dd";
	public static final String FORMAT_IONIC_ANDROID = "MMM dd yyyy";
	
	/**
	 * Constructeur privé, classe statique
	 */
	private DateUtils() {
	}

	/**
	 * Convert dd/MM/yyyy string to calendar
	 * @param str formated string
	 * @param lenient
	 * @return Calendar
	 * @throws ParseException
	 */
	public static final Calendar stringToCalendar(final String str, boolean lenient) throws ParseException {
		return stringToCalendar(str, FORMAT_DATE, lenient);
	}

	/**
	 * Convert formated string to calendar
	 * @param str formated string
	 * @param format (ex : dd/MM/yyyy)
	 * @param lenient
	 * @return Calendar
	 * @throws ParseException
	 */
	public static final Calendar stringToCalendar(final String str, String format, boolean lenient)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		sdf.setLenient(lenient);
		Date date = sdf.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Convert dd/MM/yyyy string to calendar
	 * @param str formated string
	 * @return Calendar
	 * @throws ParseException
	 */
	public static final Calendar stringToCalendar(final String str) throws ParseException {
		return stringToCalendar(str, true);
	}

	/**
	 * Convert string to calendar
	 * @param str formated string
	 * @param format (ex : dd/MM/yyyy)
	 * @return Calendar
	 * @throws ParseException
	 */
	public static final Calendar stringToCalendar(final String str, String format) throws ParseException {
		return stringToCalendar(str, format, true);
	}

	/**
	 * Convert string to calendar
	 * @param str formated string
	 * @param format (ex : dd/MM/yyyy)
	 * @return Calendar (current time if parse failure).
	 * @throws ParseException
	 */
	public static final Calendar stringToCalendarQuietly(final String str, String format) {
		try {
			return stringToCalendar(str, format, true);
		} catch (ParseException e) {
			return getCurrentTime();
		}
	}
	
	/**
	 * Transformer une chaine en date ou garder la date courante
	 * @param str
	 * @param format
	 * @param defaultValue
	 * @return
	 */
	public static final Calendar stringToCalendarQuietly(final String str, String format, Calendar defaultValue) {
		try {
			return stringToCalendar(str, format, true);
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	/**
	 * Specialement pour la version ionic
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static final Calendar ionicParse(final String str, Calendar defaultValue){
		String date = str.substring(4,15);
		return stringToCalendarQuietly(date, FORMAT_IONIC_ANDROID, defaultValue);
	}

	/**
	 * Convert Calendar to String
	 * @param Calendar c
	 * @param format (ex : dd/MM/yyyy)
	 * @return String
	 */
	public static final String calendarToString(Calendar c, String format) {
		String rtn = null;
		if (c != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
			rtn = sdf.format(c.getTime());
		}
		return rtn;
	}

	/**
	 * Convert Calendar to String (dd/MM/yyyy)
	 * @param Calendar c
	 * @return String
	 */
	public static final String calendarToString(Calendar c) {
		return calendarToString(c, FORMAT_DATE);
	}

	/**
	 * Get current date
	 * @return Calendar
	 */
	public static final Calendar getCurrentTime() {
		return (Calendar) Calendar.getInstance().clone();
	}
	
	/**
	 * Verifier si on a dépassé un delais ou pas
	 * @param date
	 * @param nbrOfMinutes
	 * @return
	 */
	public static final boolean verifyDelay(Calendar date, Integer nbrOfMinutes){
		if(null == date || null == nbrOfMinutes){
			return false;
		}
		if(Math.abs(GregorianCalendar.getInstance().get(Calendar.MINUTE) - date.get(Calendar.MINUTE)) >= nbrOfMinutes){
			return true;
		}
		return false;
	}
}
