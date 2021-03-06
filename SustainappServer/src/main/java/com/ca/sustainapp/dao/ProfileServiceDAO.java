package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.ProfileCriteria;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.ProfileRepository;
import com.ca.sustainapp.specification.ProfileSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2107
 * @verion 1.0
 */
@Service("profileService")
public class ProfileServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	ProfileRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public ProfileEntity getById(Long id){
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
	public Long createOrUpdate(ProfileEntity entity){
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
	public List<ProfileEntity> getAll(){
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
	public SearchResult<ProfileEntity> searchByCriteres(ProfileCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<ProfileEntity> spec = ProfileSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<ProfileEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<ProfileEntity> result = initSearchResult(startIndex, maxResults);
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
	public List<ProfileEntity> searchByKeywords(String query, Integer maximum){
		return repository.searchByKeywords(query, new PageRequest(0, maximum));
	}
	
	/**
	 * Search by fullname
	 * @param fullName
	 * @param maximum
	 * @return
	 */
	@Transactional
	public List<ProfileEntity> searchByFulName(String fullName, Integer visibility, Integer maximum){
		return repository.searchByFullName(fullName, visibility, new PageRequest(0, maximum));
	}
}