package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.TopicEntity;

/**
 * Repository for TopicEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface TopicRepository extends JpaSpecificationExecutor<TopicEntity>, JpaRepository<TopicEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM TopicEntity AS t WHERE t.id = :id")
	Integer countById(@Param("id") Long id);

}