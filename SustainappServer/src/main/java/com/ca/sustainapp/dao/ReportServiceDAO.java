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

import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.ReportRepository;
import com.ca.sustainapp.specification.ReportSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2107
 * @verion 1.0
 */
@Service("reportService")
public class ReportServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	ReportRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public ReportEntity getById(Long id){
		if(null == id){
			return null;
		}
		ReportEntity entity = repository.findOne(id);
		return entity.setBase64(encodeBase64String(entity.getDocument()));
	}
	
	/**
	 * Create a new entity
	 * @param entity
	 * @return
	 */
	@Modifying
	@Transactional
	public Long createOrUpdate(ReportEntity entity){
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
	public List<ReportEntity> getAll(){
		List<ReportEntity> listResult = repository.findAll();
		for(ReportEntity entity : listResult){
			entity.setBase64(encodeBase64String(entity.getDocument()));
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
	public SearchResult<ReportEntity> searchByCriteres(ReportCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<ReportEntity> spec = ReportSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<ReportEntity> page = repository.findAll(spec, paginator);
		
		SearchResult<ReportEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		for(ReportEntity entity : result.getResults()){
			entity.setBase64(encodeBase64String(entity.getDocument()));
		}
		return result;
	}

}