package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.ParticipationCriteria;
import com.ca.sustainapp.entities.ParticipationEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
public class ParticipationSpecification {
	/**
	 * private constructor
	 */
	private ParticipationSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ChampsEntity>
	 */
	public static Specification<ParticipationEntity> searchByCriteres(final ParticipationCriteria criteres) {
		return new Specification<ParticipationEntity>() {
			@Override
			public Predicate toPredicate(Root<ParticipationEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getTargetId()) {
						Predicate p = cb.equal(root.<Long> get("targetId"), criteres.getTargetId());
						listeCond.add(p);
					}
					
					if (null != criteres.getChallengeId()) {
						Predicate p = cb.equal(root.<Long> get("challengeId"), criteres.getChallengeId());
						listeCond.add(p);
					}
					

					if (null != criteres.getTitle()) {
						Predicate p = cb.like(cb.lower(root.<String> get("title")), criteres.getTitle().toLowerCase() + "%");
						listeCond.add(p);
					}
					

					if (null != criteres.getAbout()) {
						Predicate p = cb.like(cb.lower(root.<String> get("about")), criteres.getAbout().toLowerCase() + "%");
						listeCond.add(p);
					}
					

					if (null != criteres.getTargetType()) {
						Predicate p = cb.like(cb.lower(root.<String> get("targetType")), criteres.getTargetType().toLowerCase() + "%");
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
