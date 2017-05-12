package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.entities.CityEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
public class CitySpecification {

	/**
	 * private constructor
	 */
	private CitySpecification(){
		
	}

	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<CityEntity>
	 */
	public static Specification<CityEntity> searchByCriteres(final CityCriteria criteres) {
		return new Specification<CityEntity>() {
			@Override
			public Predicate toPredicate(Root<CityEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getUserId()) {
						Predicate p = cb.equal(root.<Long> get("userId"), criteres.getUserId());
						listeCond.add(p);
					}

					if (null != criteres.getActif()) {
						Predicate p = cb.equal(root.<Integer> get("actif"), criteres.getActif());
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