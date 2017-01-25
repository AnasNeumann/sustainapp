package com.ca.sustainapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.ProfileEntity;


/**
 * Repository for profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2017
 * @version 1.0
 */
@Repository
public interface ProfileRepository extends JpaSpecificationExecutor<ProfileEntity>, JpaRepository<ProfileEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM ProfileEntity AS p WHERE p.id = :id")
	Integer countById(@Param("id") Long id); 
	
}
