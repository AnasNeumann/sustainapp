package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.ChallengeCriteria;
import com.ca.sustainapp.entities.ChallengeEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.ChallengeRepository;
import com.ca.sustainapp.specification.ChallengeSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2107
 * @verion 1.0
 */
@Service("challengeService")
public class ChallengeServiceDAO extends GenericServiceDAO {

	/**
	 * Le repository
	 */
	@Autowired
	ChallengeRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public ChallengeEntity getById(Long id){
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
	public Long createOrUpdate(ChallengeEntity entity){
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
	public List<ChallengeEntity> getAll(){
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
	public SearchResult<ChallengeEntity> searchByCriteres(ChallengeCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<ChallengeEntity> spec = ChallengeSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<ChallengeEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<ChallengeEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}

	/**
	 * get the total number of entity
	 * @return
	 */
	public Integer total(){
		return repository.total();
	}
	
	/**
	 * Count entity by type
	 * @param type
	 * @return
	 */
	public Integer countByType(Long type){
		return repository.countByType(type);
	}
}
