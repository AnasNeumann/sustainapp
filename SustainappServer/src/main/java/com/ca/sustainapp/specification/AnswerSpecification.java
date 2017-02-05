package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.AnswerCriteria;
import com.ca.sustainapp.entities.AnswerEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class AnswerSpecification {

	/**
	 * private constructor
	 */
	private AnswerSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ChampsEntity>
	 */
	public static Specification<AnswerEntity> searchByCriteres(final AnswerCriteria criteres) {
		return new Specification<AnswerEntity>() {
			@Override
			public Predicate toPredicate(Root<AnswerEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getQuestionId()) {
						Predicate p = cb.equal(root.<Long> get("questionId"), criteres.getQuestionId());
						listeCond.add(p);
					}

					if (null != criteres.getMessage()) {
						Predicate p = cb.like(cb.lower(root.<String> get("message")), criteres.getMessage().toLowerCase() + "%");
						listeCond.add(p);
					}

					if (null != criteres.getIsTrue()) {
						Predicate p = cb.equal(root.<Boolean> get("isTrue"), criteres.getIsTrue());
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
