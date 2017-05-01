package com.ca.sustainapp.comparators;

import java.util.Comparator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.entities.ParticipationEntity;

/**
 * Classe de comparaison de deux participation selon :
 * 1/ le nombre de vote
 * 2/ puis en cas d'égalité par la plus récente
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
@Component
@Scope("singleton")
public class ParticipationComparator implements Comparator<ParticipationEntity>  {

	/**
	 * Redifinition de la méthode de comparaison
	 * @param p1
	 * @param p2
	 * @return
	 */
	@Override
	public int compare(ParticipationEntity p1, ParticipationEntity p2) {		
		if(null != p1.getVotes() && null!= p2.getVotes()){
			if(p1.getVotes().size() > p2.getVotes().size()){
				return -1;
			}else if(p1.getVotes().size() < p2.getVotes().size()){
				return 1;
			} else {
				return p1.getTimestamps().compareTo(p2.getTimestamps());
			}
		}else {
			return p2.getTimestamps().compareTo(p1.getTimestamps());
		}
	}
}