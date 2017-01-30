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

					if (null != criteres.getProfilId()) {
						Predicate p = cb.equal(root.<Long> get("profilId"), criteres.getProfilId());
						listeCond.add(p);
					}
					
					if (null != criteres.getChallengeId()) {
						Predicate p = cb.equal(root.<Long> get("challengeId"), criteres.getChallengeId());
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
