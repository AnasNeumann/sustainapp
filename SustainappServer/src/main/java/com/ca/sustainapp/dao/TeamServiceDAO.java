package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.TeamCriteria;
import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.specification.TeamSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2107
 * @verion 1.0
 */
@Service("teamService")
public class TeamServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	TeamRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public TeamEntity getById(Long id){
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
	public Long createOrUpdate(TeamEntity entity){
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
	public List<TeamEntity> getAll(){
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
	public SearchResult<TeamEntity> searchByCriteres(TeamCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<TeamEntity> spec = TeamSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<TeamEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<TeamEntity> result = initSearchResult(startIndex, maxResults);
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
	public List<TeamEntity> searchByKeywords(List<String> keywords, Integer maximum){
		return repository.searchByKeywords(keywords, new PageRequest(0, maximum));
	}
}
