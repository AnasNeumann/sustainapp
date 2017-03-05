package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.TeamEntity;

/**
 * Repository for TeamEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/01/2017
 * @version 1.0
 */
@Repository
public interface TeamRepository extends JpaSpecificationExecutor<TeamEntity>, JpaRepository<TeamEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM TeamEntity AS t WHERE t.id = :id")
	Integer countById(@Param("id") Long id);

	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM TeamEntity t WHERE t.id = :id")
	void delete(@Param("id") Long id);
	
	/**
	 * Select all teams by keywords
	 * @param Keywords
	 * @return
	 */
	@Query("FROM TeamEntity t where (t.name in :keywords)")
	List<TeamEntity> searchByKeywords(@Param("keywords") List<String> Keywords, Pageable pageable);
}