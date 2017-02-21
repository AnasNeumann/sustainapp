package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.ChallengeVoteEntity;

/**
 * Repository for ChallengeVoteEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface ChallengeVoteRepository extends JpaSpecificationExecutor<ChallengeVoteEntity>, JpaRepository<ChallengeVoteEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ChallengeVoteEntity AS c WHERE c.id = :id")
	Integer countById(@Param("id") Long id);

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ChallengeVoteEntity c WHERE c.id = :id")
	void delete(@Param("id") Long id);
}