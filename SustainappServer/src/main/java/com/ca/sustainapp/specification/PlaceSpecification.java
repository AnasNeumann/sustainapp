package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.PlaceCriteria;
import com.ca.sustainapp.entities.PlaceEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
public class PlaceSpecification {

	/**
	 * private constructor
	 */
	private PlaceSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<PlaceEntity>
	 */
	public static Specification<PlaceEntity> searchByCriteres(final PlaceCriteria criteres) {
		return new Specification<PlaceEntity>() {
			@Override
			public Predicate toPredicate(Root<PlaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getCityId()) {
						Predicate p = cb.equal(root.<Long> get("cityId"), criteres.getCityId());
						listeCond.add(p);
					}
					
					if (null != criteres.getLongitude()) {
						Predicate p = cb.equal(root.<Integer> get("longitude"), criteres.getLongitude());
						listeCond.add(p);
					}
					
					if (null != criteres.getLatitude()) {
						Predicate p = cb.equal(root.<Integer> get("latitude"), criteres.getLatitude());
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
