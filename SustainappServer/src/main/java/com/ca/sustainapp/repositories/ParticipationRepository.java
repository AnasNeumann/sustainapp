package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.ParticipationEntity;

/**
 * Repository for ParticipationRepository
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface ParticipationRepository extends JpaSpecificationExecutor<ParticipationEntity>, JpaRepository<ParticipationEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ParticipationEntity AS p WHERE p.id = :id")
	Integer countById(@Param("id") Long id);

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ParticipationEntity p WHERE p.id = :id")
	void delete(@Param("id") Long id);
	
	
	/**
	 * get the total of entities
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM ParticipationEntity")
	Integer total();
}