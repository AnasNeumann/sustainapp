package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.BadgeCriteria;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.BadgeRepository;
import com.ca.sustainapp.specification.BadgeSpecification;

/**
 * data access object service for Badge
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2107
 * @verion 1.0
 */
@Service("badgeService")
public class BadgeServiceDAO extends GenericServiceDAO {

	/**
	 * Le repository
	 */
	@Autowired
	BadgeRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public BadgeEntity getById(Long id){
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
	public Long createOrUpdate(BadgeEntity entity){
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
	public List<BadgeEntity> getAll(){
		return repository.findAll();
	}

	/**
	 * search by criteria
	 * @param criteria
	 * @param startIndex
	 * @param maxResults
	 * @return
	 */
	@Transactional
	public SearchResult<BadgeEntity> searchByCriteres(BadgeCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<BadgeEntity> spec = BadgeSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<BadgeEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<BadgeEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}
}
