package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.AnswerCategoryEntity;

/**
 * Repository for answer's categories
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 07/04/2017
 * @version 1.0
 */
@Repository
public interface AnswerCategoryRepository extends JpaSpecificationExecutor<AnswerCategoryEntity>, JpaRepository<AnswerCategoryEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM AnswerCategoryEntity AS a WHERE a.id = :id")
	Integer countById(@Param("id") Long id); 

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM AnswerCategoryEntity a WHERE a.id = :id")
	void delete(@Param("id") Long id);
}