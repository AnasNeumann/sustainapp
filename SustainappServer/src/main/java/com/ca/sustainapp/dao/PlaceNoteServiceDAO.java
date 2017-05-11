package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.PlaceNoteCriteria;
import com.ca.sustainapp.entities.PlaceNoteEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.PlaceNoteRepository;
import com.ca.sustainapp.specification.PlaceNoteSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2107
 * @verion 1.0
 */
@Service("placeNoteService")
public class PlaceNoteServiceDAO extends GenericServiceDAO{
	/**
	 * Le repository
	 */
	@Autowired
	PlaceNoteRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public PlaceNoteEntity getById(Long id){
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
	public Long createOrUpdate(PlaceNoteEntity entity){
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
	public List<PlaceNoteEntity> getAll(){
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
	public SearchResult<PlaceNoteEntity> searchByCriteres(PlaceNoteCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<PlaceNoteEntity> spec = PlaceNoteSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<PlaceNoteEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<PlaceNoteEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}
	
	/**
	 * get the total number of Place notes
	 * @return
	 */
	public Integer total(){
		return repository.total();
	}
	
	/**
	 * get the total number of note by a specific score
	 * @param score
	 * @return
	 */
	public Integer countByScore(Integer score){
		return repository.countByScore(score);
	}
}