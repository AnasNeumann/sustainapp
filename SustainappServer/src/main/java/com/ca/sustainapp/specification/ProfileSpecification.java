package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.ProfileCriteria;
import com.ca.sustainapp.entities.ProfileEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class ProfileSpecification {

	/**
	 * private constructor
	 */
	private ProfileSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ProfileEntity>
	 */
	public static Specification<ProfileEntity> searchByCriteres(final ProfileCriteria criteres) {
		return new Specification<ProfileEntity>() {
			@Override
			public Predicate toPredicate(Root<ProfileEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getBornDate()) {
						Predicate p = cb.equal(root.<Calendar> get("bornDate"), criteres.getBornDate());
						listeCond.add(p);
					}

					if (null != criteres.getFirstName()) {
						Predicate p = cb.like(cb.lower(root.<String> get("firstName")), criteres.getFirstName().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getLastName()) {
						Predicate p = cb.like(cb.lower(root.<String> get("lastName")), criteres.getLastName().toLowerCase() + "%");
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
