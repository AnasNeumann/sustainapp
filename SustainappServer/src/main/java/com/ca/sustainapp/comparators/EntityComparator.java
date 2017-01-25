package com.ca.sustainapp.comparators;

import java.util.Comparator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.entities.GenericEntity;

/**
 * Classe de comparaison de deux entities selon le temps
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 24/01/2017
 * @version 1.0
 */
@Component
@Scope("singleton")
public class EntityComparator implements Comparator<GenericEntity>  {

	/**
	 * Redifinition de la méthode de comparaison
	 * @param e1
	 * @param e2
	 * @return
	 */
	@Override
	public int compare(GenericEntity e1, GenericEntity e2) {
		if (null != e1 && null != e2 && null != e1.getTimestamps() && null != e2.getTimestamps()) {
			return e1.getTimestamps().compareTo(e2.getTimestamps());
		}
		return 0;
	}

}
