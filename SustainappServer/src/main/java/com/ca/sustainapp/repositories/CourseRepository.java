package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.CourseEntity;

/**
 * Repository for CourseEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface CourseRepository extends JpaSpecificationExecutor<CourseEntity>, JpaRepository<CourseEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM CourseEntity AS c WHERE c.id = :id")
	Integer countById(@Param("id") Long id); 

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM CourseEntity c WHERE c.id = :id")
	void delete(@Param("id") Long id);
	
	/**
	 * Select all teams by keywords
	 * @param Keywords
	 * @return
	 */
	@Query("FROM CourseEntity c where LOWER(c.title) LIKE  CONCAT('%',LOWER(:keywords),'%')")
	List<CourseEntity> searchByKeywords(@Param("keywords") String Keywords, Pageable pageable);
}
