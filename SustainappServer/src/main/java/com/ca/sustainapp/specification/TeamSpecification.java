package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.TeamCriteria;
import com.ca.sustainapp.entities.TeamEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class TeamSpecification {

	/**
	 * private constructor
	 */
	private TeamSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<TeamEntity>
	 */
	public static Specification<TeamEntity> searchByCriteres(final TeamCriteria criteres) {
		return new Specification<TeamEntity>() {
			@Override
			public Predicate toPredicate(Root<TeamEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getLevel()) {
						Predicate p = cb.equal(root.<Integer> get("level"), criteres.getLevel());
						listeCond.add(p);
					}

					if (null != criteres.getName()) {
						Predicate p = cb.like(cb.lower(root.<String> get("name")), criteres.getName().toLowerCase() + "%");
						listeCond.add(p);
					}
					
					
					if (null != criteres.getTimestamps()) {
						Predicate p = cb.equal(root.<Calendar> get("timestamps"), criteres.getTimestamps());
						listeCond.add(p);
					}

				}
				Predicate[] cond = new Predicate[listeCond.size()];
				listeCond.toArray(cond);
				return cb.and(cond);
			}
		};
	}
}
