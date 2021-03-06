package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.ResearchEntity;

/**
 * Repository for ResearchEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 08/05/2017
 * @version 1.0
 */
@Repository
public interface ResearchRepository extends JpaSpecificationExecutor<ResearchEntity>, JpaRepository<ResearchEntity, Long> {

	/**
	 * Count entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ResearchEntity AS r WHERE r.id = :id")
	Integer countById(@Param("id") Long id); 

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ResearchEntity r WHERE r.id = :id")
	void delete(@Param("id") Long id);
	
	/**
	 * get the total of research
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM ResearchEntity")
	Integer total();
	
	/**
	 * get the most done research
	 * @param pageable
	 * @return
	 */
	@Query("SELECT r.query FROM ResearchEntity as r GROUP BY r.query ORDER BY COUNT(r) DESC")
	List<String> mostSeen(Pageable pageable);
}