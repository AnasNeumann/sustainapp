package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.QuestionCriteria;
import com.ca.sustainapp.entities.QuestionEntity;

public class QuestionSpecification {

	/**
	 * private constructor
	 */
	private QuestionSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<QuestionEntity>
	 */
	public static Specification<QuestionEntity> searchByCriteres(final QuestionCriteria criteres) {
		return new Specification<QuestionEntity>() {
			@Override
			public Predicate toPredicate(Root<QuestionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}
					
					if (null != criteres.getTopicId()) {
						Predicate p = cb.equal(root.<Long> get("topicId"), criteres.getTopicId());
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
