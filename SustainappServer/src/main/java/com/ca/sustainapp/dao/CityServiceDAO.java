package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.CityCriteria;
import com.ca.sustainapp.entities.CityEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.CityRepository;
import com.ca.sustainapp.specification.CitySpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2107
 * @verion 1.0
 */
@Service("cityService")
public class CityServiceDAO extends GenericServiceDAO{
	
	/**
	 * Le repository
	 */
	@Autowired
	CityRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public CityEntity getById(Long id){
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
	public Long createOrUpdate(CityEntity entity){
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
	public List<CityEntity> getAll(){
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
	public SearchResult<CityEntity> searchByCriteres(CityCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<CityEntity> spec = CitySpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<CityEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<CityEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}
	
	/**
	 * get the total number of Citis
	 * @return
	 */
	public Integer total(){
		return repository.total();
	}
	
	/**
	 * Search by keywords and Max results
	 * @param Keywords
	 * @param maximum
	 * @return
	 */
	@Transactional
	public List<CityEntity> searchByKeywords(String keywords, Integer maximum){
		return repository.searchByKeywords(keywords, new PageRequest(0, maximum));
	}
}
