package com.ca.sustainapp.utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'outils pour les list et map
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Bilel Dhaouadi <dhaouadi.bil@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class ListUtils {
	
	/**
	 * Constructeur priv�, classe statique
	 */
	private ListUtils(){
		
	}
	
	/**
	 * Recuperation d'uniquement X element d'une liste
	 * 
	 * @param input
	 * @param maxSeeked
	 * @return
	 */
	public static <E> List<E> getOnlyXelements(List<? extends E> input, int maxSeeked) {
		return getElementsInInterval(input, 0, maxSeeked);
	}

	/**
	 * Retourner une liste contenant les [totalElements] �l�ments de la liste
	 * [input] � partir de [startIndex]
	 * 
	 * @param input
	 * @param startIndex
	 * @param totalElements
	 * @return
	 */
	public static <E> List<E> getElementsInInterval(List<? extends E> input, int startIndex, int totalElements) {
		List<E> result = new ArrayList<E>();
		int total = 0;
		int currentIndex = 0;
		for (E elt : input) {
			if (currentIndex >= startIndex) {
				total++;
				if (totalElements >= total) {
					result.add(elt);
				} else {
					return result;
				}
			}
			currentIndex++;
		}
		return result;
	}
	
	/**
	 * Methode d'inversion d'une liste chain�e
	 * @param input
	 * @return
	 */
	public static <E> List<E> reverseList(List<? extends E> input){
		List<E> result = new ArrayList<E>();
		for(int i = input.size()-1; i>=0; i--){
			result.add(input.get(i));
		}
		return result;
	}

}
