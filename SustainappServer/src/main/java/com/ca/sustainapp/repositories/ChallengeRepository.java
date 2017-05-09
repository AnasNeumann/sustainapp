package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.ChallengeEntity;

/**
 * Repository for ChallengeEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface ChallengeRepository  extends JpaSpecificationExecutor<ChallengeEntity>, JpaRepository<ChallengeEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ChallengeEntity AS c WHERE c.id = :id")
	Integer countById(@Param("id") Long id); 
	
	/**
	 * count entities by type
	 * @param type
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM ChallengeEntity AS c WHERE c.challengeType.id = :type")
	Integer countByType(@Param("type") Long type);
	
	/**
	 * get the total of entities
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM ChallengeEntity")
	Integer total();
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ChallengeEntity c WHERE c.id = :id")
	void delete(@Param("id") Long id);
}