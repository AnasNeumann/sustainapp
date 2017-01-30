package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.TopicValidationCriteria;
import com.ca.sustainapp.entities.TopicValidationEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class TopicValidationSpecification {

	/**
	 * private constructor
	 */
	private TopicValidationSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ChampsEntity>
	 */
	public static Specification<TopicValidationEntity> searchByCriteres(final TopicValidationCriteria criteres) {
		return new Specification<TopicValidationEntity>() {
			@Override
			public Predicate toPredicate(Root<TopicValidationEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
					
					if (null != criteres.getTopicId()) {
						Predicate p = cb.equal(root.<Long> get("topicId"), criteres.getTopicId());
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
