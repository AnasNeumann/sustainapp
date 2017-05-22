package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.VisitEntity;

/**
 * Repository for VisitEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 18/05/2017
 * @version 1.0
 */
@Repository
public interface VisitRepository extends JpaSpecificationExecutor<VisitEntity>, JpaRepository<VisitEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM VisitEntity AS v WHERE v.id = :id")
	Integer countById(@Param("id") Long id); 
	
	/**
	 * get the total of entities
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM VisitEntity")
	Integer total();
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM VisitEntity v WHERE v.id = :id")
	void delete(@Param("id") Long id);
	
	/**
	 * get the x most visited place id
	 * @param pageable
	 * @return
	 */
	@Query("SELECT v.placeId FROM VisitEntity as v GROUP BY v.placeId ORDER BY COUNT(v) DESC")
	List<Long> mostSeen(Pageable pageable);
}
