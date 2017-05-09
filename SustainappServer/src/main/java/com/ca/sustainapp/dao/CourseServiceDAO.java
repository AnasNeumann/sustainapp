package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.CourseCriteria;
import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.CourseRepository;
import com.ca.sustainapp.specification.CourseSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2107
 * @verion 1.0
 */
@Service("courseService")
public class CourseServiceDAO extends GenericServiceDAO {

	/**
	 * Le repository
	 */
	@Autowired
	CourseRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public CourseEntity getById(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	/**
	 * Create a new entity
	 * @param entity
	 * @return
	 */
	@Modifying
	@Transactional
	public Long createOrUpdate(CourseEntity entity){
		return repository.saveAndFlush(entity).getId();
	}
	
	/**
	 * Delete an entity by Id
	 * @param id
	 */
	@Modifying
	@Transactional
	public void delete(Long id){
		Integer nb = repository.countById(id);
		if (0 < nb) {
			repository.delete(id);
		}
	}
	
	/**
	 * get All
	 * @return
	 */
	@Transactional
	public List<CourseEntity> getAll(){
		return  repository.findAll();
	}

	/**
	 * search by criteria
	 * @param criteria
	 * @param startIndex
	 * @param maxResults
	 * @return
	 */
	@Transactional
	public SearchResult<CourseEntity> searchByCriteres(CourseCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<CourseEntity> spec = CourseSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<CourseEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<CourseEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}
	
	/**
	 * Search by keywords and Max results
	 * @param Keywords
	 * @param maximum
	 * @return
	 */
	@Transactional
	public List<CourseEntity> searchByKeywords(String keywords, Integer maximum){
		return repository.searchByKeywords(keywords, new PageRequest(0, maximum));
	}
	
	/**
	 * Count courses by type
	 * @param type
	 * @return
	 */
	public Integer countByType(Long type){
		return repository.countByType(type);
	}
	
	/**
	 * get the most seen courses
	 * @return
	 */
	public List<CourseEntity> mostSeen(){
		return repository.mostSeen(new PageRequest(0, 5));
	}
	
	/**
	 * get the total number of courses
	 * @return
	 */
	public Integer total(){
		return repository.total();
	}
}
