package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.entities.TopicEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class TopicSpecification {

	/**
	 * private constructor
	 */
	private TopicSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ChampsEntity>
	 */
	public static Specification<TopicEntity> searchByCriteres(final TopicCriteria criteres) {
		return new Specification<TopicEntity>() {
			@Override
			public Predicate toPredicate(Root<TopicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> listeCond = new ArrayList<Predicate>();
				if (null != criteres) {
					if (null != criteres.getId()) {
						Predicate p = cb.equal(root.<Long> get("id"), criteres.getId());
						listeCond.add(p);
					}

					if (null != criteres.getCurseId()) {
						Predicate p = cb.equal(root.<Long> get("curseId"), criteres.getCurseId());
						listeCond.add(p);
					}
					
					if (null != criteres.getNumero()) {
						Predicate p = cb.equal(root.<Integer> get("numero"), criteres.getNumero());
						listeCond.add(p);
					}
					
					if (null != criteres.getTitle()) {
						Predicate p = cb.like(cb.lower(root.<String> get("title")), criteres.getTitle().toLowerCase() + "%");
						listeCond.add(p);
					}
					
					if (null != criteres.getContent()) {
						Predicate p = cb.like(cb.lower(root.<String> get("content")), criteres.getContent().toLowerCase() + "%");
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
