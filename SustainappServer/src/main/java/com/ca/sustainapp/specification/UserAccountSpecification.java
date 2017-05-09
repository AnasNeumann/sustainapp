package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.UserAccountCriteria;
import com.ca.sustainapp.entities.UserAccountEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class UserAccountSpecification {
	
	/**
	 * private constructor
	 */
	private UserAccountSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<UserAccountEntity>
	 */
	public static Specification<UserAccountEntity> searchByCriteres(final UserAccountCriteria criteres) {
		return new Specification<UserAccountEntity>() {
			@Override
			public Predicate toPredicate(Root<UserAccountEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getMail()) {
						Predicate p = cb.like(cb.lower(root.<String> get("mail")), criteres.getMail().toLowerCase() + "%");
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
