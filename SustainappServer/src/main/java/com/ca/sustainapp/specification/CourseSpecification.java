package com.ca.sustainapp.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.entities.CourseEntity;

/**
 * specification for database research
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
public class CourseSpecification {
	
	/**
	 * private constructor
	 */
	private CourseSpecification(){
		
	}
	
	/**
	 * Recherche des Champs par critères.
	 * 
	 * @param criteres
	 * @return Specification<ResearchEntity>
	 */
	public static Specification<CourseEntity> searchByCriteres(final CourseCriteria criteres) {
		return new Specification<CourseEntity>() {
			@Override
			public Predicate toPredicate(Root<CourseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
					
					if (null != criteres.getViews()) {
						Predicate p = cb.equal(root.<Integer> get("views"), criteres.getViews());
						listeCond.add(p);
					}

					if (null != criteres.getTitle()) {
						Predicate p = cb.like(cb.lower(root.<String> get("title")), criteres.getTitle().toLowerCase() + "%");
						listeCond.add(p);
					}
					
					if (null != criteres.getLanguage()) {
						Predicate p = cb.like(cb.lower(root.<String> get("language")), criteres.getLanguage().toLowerCase() + "%");
						listeCond.add(p);
					}
					
					if (null != criteres.getAbout()) {
						Predicate p = cb.like(cb.lower(root.<String> get("about")), criteres.getAbout().toLowerCase() + "%");
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
