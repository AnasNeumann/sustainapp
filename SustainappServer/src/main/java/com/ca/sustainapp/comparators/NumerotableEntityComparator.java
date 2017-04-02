package com.ca.sustainapp.comparators;

import java.util.Comparator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.entities.GenericNumerotableEntity;

/**
 * Classe de comparaison de deux entities selon le numero
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 02/04/2017
 * @version 1.0
 */
@Component
@Scope("singleton")
public class NumerotableEntityComparator implements Comparator<GenericNumerotableEntity>  {

	/**
	 * Redifinition de la méthode de comparaison
	 * @param e1
	 * @param e2
	 * @return
	 */
	@Override
	public int compare(GenericNumerotableEntity e1, GenericNumerotableEntity e2) {
		if (null != e1 && null != e2 && null != e1.getNumero() && null != e2.getNumero()) {
			return e1.getNumero().compareTo(e2.getNumero());
		}
		return 0;
	}

}
