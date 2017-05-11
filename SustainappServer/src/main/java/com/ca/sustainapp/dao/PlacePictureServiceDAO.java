package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.PlacePictureCriteria;
import com.ca.sustainapp.entities.PlacePictureEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.PlacePictureRepository;
import com.ca.sustainapp.specification.PlacePictureSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2107
 * @verion 1.0
 */
@Service("placePictureService")
public class PlacePictureServiceDAO extends GenericServiceDAO{
	/**
	 * Le repository
	 */
	@Autowired
	PlacePictureRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public PlacePictureEntity getById(Long id){
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
	public Long createOrUpdate(PlacePictureEntity entity){
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
	public List<PlacePictureEntity> getAll(){
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
	public SearchResult<PlacePictureEntity> searchByCriteres(PlacePictureCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<PlacePictureEntity> spec = PlacePictureSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<PlacePictureEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<PlacePictureEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}
	
	/**
	 * get the total number of Place pictures
	 * @return
	 */
	public Integer total(){
		return repository.total();
	}
}
