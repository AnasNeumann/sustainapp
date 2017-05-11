package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.PlaceNoteEntity;

/**
 * Repository for PlaceNoteEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Repository
public interface PlaceNoteRepository extends JpaSpecificationExecutor<PlaceNoteEntity>, JpaRepository<PlaceNoteEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM PlaceNoteEntity AS p WHERE p.id = :id")
	Integer countById(@Param("id") Long id); 
	
	/**
	 * count entities by score
	 * @param type
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM PlaceNoteEntity AS p WHERE p.score = :score")
	Integer countByScore(@Param("score") Integer score);
	
	/**
	 * get the total of entities
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM PlaceNoteEntity")
	Integer total();
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM PlaceNoteEntity p WHERE p.id = :id")
	void delete(@Param("id") Long id);
	
}