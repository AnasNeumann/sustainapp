package com.ca.sustainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.criteria.UserAccountCriteria;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.repositories.UserAccountRepository;
import com.ca.sustainapp.specification.UserAccountSpecification;

/**
 * data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2107
 * @verion 1.0
 */
@Service("userAccountService")
public class UserAccountServiceDAO extends GenericServiceDAO {
	
	/**
	 * Le repository
	 */
	@Autowired
	UserAccountRepository repository;
	
	/**
	 * Accès un seul entity par son Id
	 * @param id
	 * @return
	 */
	public UserAccountEntity getById(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	/**
	 * Connect to an account
	 * @param mail
	 * @param password
	 * @return
	 */
	public UserAccountEntity connect(String mail, String password){
		List<UserAccountEntity> listResult = repository.getByLoginAndPassword(mail, password);
		if(null != listResult && listResult.size() >0){
			return listResult.get(0);
		}
		return null;
	}
	
	/**
	 * Get by id and token
	 * @param id
	 * @param token
	 * @return
	 */
	public UserAccountEntity getByToken(Long id, String token){
		UserAccountEntity user = repository.findOne(id);
		if(user.getToken().equals(token)){
			return user;
		}
		return null;
	}
	
	/**
	 * Create a new entity
	 * @param entity
	 * @return
	 */
	@Modifying
	@Transactional
	public Long createOrUpdate(UserAccountEntity entity){
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
	public List<UserAccountEntity> getAll(){
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
	public SearchResult<UserAccountEntity> searchByCriteres(UserAccountCriteria criteria, Long startIndex, Long maxResults) {		
		Specification<UserAccountEntity> spec = UserAccountSpecification.searchByCriteres(criteria);
		PageRequest paginator = new PageRequest(startIndex.intValue(), maxResults.intValue());
		Page<UserAccountEntity> page = repository.findAll(spec, paginator);
		SearchResult<UserAccountEntity> result = initSearchResult(startIndex, maxResults);
		result.setTotalResults(page.getTotalElements()).setResults(page.getContent());
		return result;
	}

}
