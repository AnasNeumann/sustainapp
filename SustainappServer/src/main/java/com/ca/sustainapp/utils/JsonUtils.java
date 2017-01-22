package com.ca.sustainapp.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe d'outils pour la gestion des fichiers Json
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class JsonUtils {

	/**
	 * Constructeur privé, classe statique
	 */
	private JsonUtils() {
	}

	/**
	 * Convertir une Map en JSON.
	 * 
	 * @param map
	 * @return String
	 */
	public static String map2jsonQuietly(Map<?, ?> map) {
		if (!isNotEmpty(map)) {
			return SustainappConstantes.EMPTY_STRING;
		}
		String rtn = SustainappConstantes.EMPTY_STRING;
		try {
			ObjectMapper mapper = new ObjectMapper();
			rtn = mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return rtn;
	}

	/**
	 * Convertir un objet en JSON.
	 * 
	 * @param obj
	 * @param clazz
	 * @return String
	 */
	public static String objectTojsonQuietly(Object obj, Class<?> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(clazz.cast(obj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println("error detected");
		}
		return json;
	}

	/**
	 * Convertir une Liste en JSON.
	 * 
	 * @param list
	 * @return String
	 */
	public static String list2jsonQuietly(List<? extends List<String>> list) {
		if (!isNotEmpty(list)) {
			return SustainappConstantes.EMPTY_JSON;
		}
		String rtn = SustainappConstantes.EMPTY_JSON;
		try {
			ObjectMapper mapper = new ObjectMapper();
			rtn = mapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return rtn;
	}

	/**
	 * Transformer une liste en JSON
	 * 
	 * @param list
	 * @return String
	 */
	public static String genericList2jsonQuietly(List<?> list) {
		if (!isNotEmpty(list)) {
			return SustainappConstantes.EMPTY_JSON;
		}
		String rtn = SustainappConstantes.EMPTY_JSON;
		try {
			ObjectMapper mapper = new ObjectMapper();
			rtn = mapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return rtn;
	}

	/**
	 * Convertir une chaine Json en map.
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, String> json2mapQuietly(String json) {
		Map<String, String> rtn = null;
		if (isNotEmpty(json)) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				rtn = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception e) {
			}
		}
		return rtn;
	}

	/**
	 * Methode permettant de transformer un message String en un message JSON
	 * 
	 * @param message
	 * @return
	 */
	public static String formatJsonMessage(String message) {
		return "{\"message\":\"" + message + "\"}";
	}

}
