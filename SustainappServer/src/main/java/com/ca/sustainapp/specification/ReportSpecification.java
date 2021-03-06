package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.entities.ReportEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class ReportSpecification {

	/**
	 * private constructor
	 */
	private ReportSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ReportEntity>
	 */
	public static Specification<ReportEntity> searchByCriteres(final ReportCriteria criteres) {
		return new Specification<ReportEntity>() {
			@Override
			public Predicate toPredicate(Root<ReportEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
					
					if (null != criteres.getState()) {
						Predicate p = cb.equal(root.<Integer> get("state"), criteres.getState());
						listeCond.add(p);
					}

					if (null != criteres.getMessage()) {
						Predicate p = cb.like(cb.lower(root.<String> get("message")), criteres.getMessage().toLowerCase() + "%");
						listeCond.add(p);
					}
					
					if (null != criteres.getDocumentType()) {
						Predicate p = cb.like(cb.lower(root.<String> get("documentType")), criteres.getDocumentType().toLowerCase() + "%");
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
