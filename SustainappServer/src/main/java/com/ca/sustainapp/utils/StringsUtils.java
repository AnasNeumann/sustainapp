package com.ca.sustainapp.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.math.NumberUtils.isNumber;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.ca.sustainapp.boot.SustainappConstantes;

/**
 * Classe d'outils pour la gestion des Strings
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Bilel Dhaouadi <dhaouadi.bil@gmail.com>
 * @version 1.0
 * @since 17/10/2016
 */
public class StringsUtils {

	/**
	 * Constructeur priv�, classe statique
	 */
	private StringsUtils(){
		
	}
	
	/**
	 * M�thode de parsing d'une url pour construire l'embed de youtube
	 * @param url
	 * @return
	 */
	public static String buildYoutubeEmbed(String url){
		if(null == url){
			return "";
		}
		return "https://www.youtube.com/embed/"+getVideoIDFromYoutube(url);
	}
	
	/**
	 * Retrouver l'id d'une vid�o youtube � partir de l'url
	 * @param url
	 * @return
	 */
	public static String getVideoIDFromYoutube(String url){
		if(null == url){
			return null;
		}
		String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
	    Pattern compiledPattern = Pattern.compile(pattern);
	    Matcher matcher = compiledPattern.matcher(url);
	    if(matcher.find()){
	        return matcher.group();
	    }
		return "";
	}

	
	/**
	 * Formater les arguments {0}, {1}, etc d'une cha�ne
	 * 
	 * @param str
	 * @param args
	 * @param locale
	 * @return String
	 */
	public static String format(String str, Object[] args, Locale locale) {
		if (StringUtils.isEmpty(str) || null == args || args.length <= 0) {
			return str;
		}
		MessageFormat mf = new MessageFormat(str, locale);
		return mf.format(args);
	}

	/**
	 * Comparer deux chaines de carract�res potentiellements nulles
	 * 
	 * @param s1
	 * @param s2
	 * @param ignoreCase
	 * @return int (1 si s1 > s2, -1 si s1 < s2 et 0 si s1 == s2)
	 */
	public static int compareTo(String s1, String s2, boolean ignoreCase) {
		if (null == s1 && null == s2) {
			return 0;
		} else if (null == s1) {
			return -1;
		} else if (null == s2) {
			return 1;
		} else {
			return (ignoreCase) ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);
		}
	}

	/**
	 * Comparer deux chaines de carract�res potentiellements nulles
	 * 
	 * @param s1
	 * @param s2
	 * @return int (1 si s1 > s2, -1 si s1 < s2 et 0 si s1 == s2)
	 */
	public static int compareTo(String s1, String s2) {
		return compareTo(s1, s2, false);
	}

	/**
	 * Cast en String
	 * 
	 * @param Object
	 *            value
	 * @return String
	 */
	public static String objectToString(Object value) {
		return (null != value) ? value.toString() : SustainappConstantes.EMPTY_STRING;
	}

	/**
	 * Parse l'heure sous format (hh:mm ou hhmm) et retourne une liste de long
	 * 
	 * @param strListNums
	 * @return
	 */
	public static List<Long> parseHeures(String strListNums) {
		List<Long> rtn = new ArrayList<Long>();
		String[] ids = strListNums.split(":");
		if (1 == ids.length) {
			rtn.add((Long.valueOf(ids[0].substring(0, 2))));
			rtn.add((Long.valueOf(ids[0].substring(2, 4))));
		} else {
			for (String id : ids) {
				rtn.add(Long.valueOf(id));
			}
		}
		return rtn;
	}

	/**
	 * Retrouver le nom d'un getter � partir d'un nom de propri�t�.
	 * 
	 * @param propertyName
	 * @return String
	 */
	public static String getterFromProperty(String propertyName) {
		return (null == propertyName) ? null
				: "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	/**
	 * Retrouver le nom d'un setter � partir d'un nom de propri�t�.
	 * 
	 * @param propertyName
	 * @return String
	 */
	public static String setterFromProperty(String propertyName) {
		return (null == propertyName) ? null
				: "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	/**
	 * Splitter une chaine.
	 * 
	 * @param str
	 * @param separator
	 * @return List<String>
	 */
	public static List<String> safeSplit(String str, String separator) {
		if (!isNotBlank(str)) {
			return null;
		}

		List<String> rtn = new ArrayList<String>();
		String[] t = str.split(separator);
		if (null != t && t.length > 0) {
			for (String c : t) {
				rtn.add(c);
			}
		} else {
			rtn.add(str);
		}

		return rtn;
	}

	/**
	 * Comparaison "safe" de deux integers.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Integer safeCompare(Integer o1, Integer o2) {
		if (null == o1 && null == o2) {
			return 0;
		} else if (null == o1) {
			return -1;
		} else if (null == o2) {
			return 1;
		} else {
			return o1.compareTo(o2);
		}
	}

	/**
	 * Comparaison "safe" de deux calendars.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Integer safeCompare(Calendar o1, Calendar o2) {
		if (null == o1 && null == o2) {
			return 0;
		} else if (null == o1) {
			return -1;
		} else if (null == o2) {
			return 1;
		} else {
			return o1.compareTo(o2);
		}
	}

	/**
	 * Convertir une chaine en Long.
	 * 
	 * @param value
	 * @return Optional<Long>
	 */
	public static Optional<Long> parseLongQuietly(String value) {
		if (!isNumber(value)) {
			return Optional.empty();
		}

		return Optional.of(Long.valueOf(value));
	}

	/**
	 * Convertir une chaine en Long avec radix.
	 * 
	 * @param value
	 * @param radix
	 * @return Optional<Long>
	 */
	public static Optional<Long> parseLongQuietly(String value, int radix) {
		if (!isNumber(value)) {
			return Optional.empty();
		}
		try {
			return Optional.ofNullable(Long.parseLong(value, radix));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	/**
	 * Convertir une chaine en Integer.
	 * 
	 * @param value
	 * @return Optional<Integer>
	 */
	public static Optional<Integer> parseIntegerQuietly(String value) {
		if (!isNumber(value)) {
			return Optional.empty();
		}
		return Optional.of(Integer.valueOf(value));
	}

	/**
	 * Methode de parsing d'une chaine de caract�re en Long de mani�re s�curis�e
	 * @param input
	 * @return
	 */
	public static Optional<Long> parseLongQuickly(String input){
		if(null == input || !NumberUtils.isNumber(input)){
			return Optional.empty();
		}
		Long output = null;
		try {
			output = Long.parseLong(input);
        } catch (NumberFormatException nfe) {
        	return Optional.empty();
        }
		return Optional.of(output);
	}
}
