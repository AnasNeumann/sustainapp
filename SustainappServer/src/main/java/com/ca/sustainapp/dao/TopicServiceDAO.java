package com.ca.sustainapp.dao;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import com.ca.sustainapp.criteria.TopicCriteria;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.TopicRepository;
import com.ca.sustainapp.specification.TopicSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2107
 * @verion 1.0
 */
@Service("topicService")
public class TopicServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	TopicRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public TopicEntity getById(Long id){
		if(null == id){
			return null;
		}
		TopicEntity entity = repository.findOne(id);
		return entity.setBase64(encodeBase64String(entity.getPicture()));
	}
	
	/**
	 * Create a new entity
	 * @param entity
	 * @return
	 */
	@Modifying
	@Transactional
	public Long createOrUpdate(TopicEntity entity){
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
	public List<TopicEntity> getAll(){
		List<TopicEntity> listResult = repository.findAll();
		for(TopicEntity entity : listResult){
			entity.setBase64(encodeBase64String(entity.getPicture()));
		}
		return listResult;
	}

	/**
	 * search by criteria
	 * @param criteria
	 * @param startIndex
	 * @param maxResults
	 * @return
	 */
	@Transactional
	public SearchResult<TopicEntity> searchByCriteres(TopicCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<TopicEntity> spec = TopicSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<TopicEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<TopicEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		for(TopicEntity entity : result.getResults()){
			entity.setBase64(encodeBase64String(entity.getPicture()));
		}
		return result;
	}

}
