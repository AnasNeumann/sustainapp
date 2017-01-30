package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.NotificationCriteria;
import com.ca.sustainapp.entities.NotificationEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class NotificationSpecification {
	/**
	 * private constructor
	 */
	private NotificationSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ChampsEntity>
	 */
	public static Specification<NotificationEntity> searchByCriteres(final NotificationCriteria criteres) {
		return new Specification<NotificationEntity>() {
			@Override
			public Predicate toPredicate(Root<NotificationEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getCreatorId()) {
						Predicate p = cb.equal(root.<Long> get("creatorId"), criteres.getCreatorId());
						listeCond.add(p);
					}
					
					if (null != criteres.getProfilId()) {
						Predicate p = cb.equal(root.<Long> get("profilId"), criteres.getProfilId());
						listeCond.add(p);
					}
					
					if (null != criteres.getLinkId()) {
						Predicate p = cb.equal(root.<Long> get("linkId"), criteres.getLinkId());
						listeCond.add(p);
					}
					
					if (null != criteres.getLinkType()) {
						Predicate p = cb.equal(root.<Long> get("linkType"), criteres.getLinkType());
						listeCond.add(p);
					}
					
					if (null != criteres.getState()) {
						Predicate p = cb.equal(root.<Long> get("state"), criteres.getState());
						listeCond.add(p);
					}

					if (null != criteres.getMessage()) {
						Predicate p = cb.like(cb.lower(root.<String> get("message")), criteres.getMessage().toLowerCase() + "%");
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
