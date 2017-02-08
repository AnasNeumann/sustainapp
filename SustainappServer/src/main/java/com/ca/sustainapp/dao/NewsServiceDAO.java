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

import com.ca.sustainapp.criteria.NewsCriteria;
import com.ca.sustainapp.entities.NewsEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.NewsRepository;
import com.ca.sustainapp.specification.NewsSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2107
 * @verion 1.0
 */
@Service("newsService")
public class NewsServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	NewsRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public NewsEntity getById(Long id){
		if(null == id){
			return null;
		}
		NewsEntity entity = repository.findOne(id);
		return entity.setBase64(encodeBase64String(entity.getPicture()));
	}
	
	/**
	 * Create a new entity
	 * @param entity
	 * @return
	 */
	@Modifying
	@Transactional
	public Long createOrUpdate(NewsEntity entity){
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
	public List<NewsEntity> getAll(){
		List<NewsEntity> listResult = repository.findAll();
		for(NewsEntity entity : listResult){
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
	public SearchResult<NewsEntity> searchByCriteres(NewsCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<NewsEntity> spec = NewsSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<NewsEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<NewsEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		for(NewsEntity entity : result.getResults()){
			entity.setBase64(encodeBase64String(entity.getPicture()));
		}
		return result;
	}

}