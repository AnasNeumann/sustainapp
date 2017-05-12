package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.entities.PlaceNoteEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
public class PlaceNoteSpecification {

	/**
	 * private constructor
	 */
	private PlaceNoteSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<PlaceNoteEntity>
	 */
	public static Specification<PlaceNoteEntity> searchByCriteres(final PlaceNoteCriteria criteres) {
		return new Specification<PlaceNoteEntity>() {
			@Override
			public Predicate toPredicate(Root<PlaceNoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getPlaceId()) {
						Predicate p = cb.equal(root.<Long> get("placeId"), criteres.getPlaceId());
						listeCond.add(p);
					}
					
					if (null != criteres.getProfilId()) {
						Predicate p = cb.equal(root.<Long> get("profilId"), criteres.getProfilId());
						listeCond.add(p);
					}
					
					if (null != criteres.getScore()) {
						Predicate p = cb.equal(root.<Integer> get("score"), criteres.getScore());
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
