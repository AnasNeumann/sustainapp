package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.PlaceEntity;

/**
 * Repository for PlaceEntity
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 11/05/2017
 * @version 1.0
 */
@Repository
public interface PlaceRepository extends JpaSpecificationExecutor<PlaceEntity>, JpaRepository<PlaceEntity, Long> {
	
	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM PlaceEntity AS p WHERE p.id = :id")
	Integer countById(@Param("id") Long id); 
	
	/**
	 * get the total of entities
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM PlaceEntity")
	Integer total();
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM PlaceEntity p WHERE p.id = :id")
	void delete(@Param("id") Long id);

	/**
	 * Get all place near to the user position
	 * @param lng
	 * @param lat
	 * @return List<PlaceEntity>
	 */
	@Modifying
	@Query("FROM PlaceEntity p WHERE (ABS(p.longitude - :lng) < 1 ) AND (ABS(p.latitude - :lat) < 1 )")
	List<PlaceEntity> getNear(@Param("lng") Float lng, @Param("lat") Float lat);
}