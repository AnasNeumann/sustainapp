package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM ProfileEntity p WHERE p.id = :id")
	void delete(@Param("id") Long id);

	/**
	 * Select all profiles by keywords
	 * @param Keywords
	 * @return
	 */
	@Query("FROM ProfileEntity p where (LOWER(p.firstName) LIKE CONCAT('%',LOWER(:keywords),'%')) OR (LOWER(p.lastName) LIKE CONCAT('%',LOWER(:keywords),'%'))")
	List<ProfileEntity> searchByKeywords(@Param("keywords") String Keywords, Pageable pageable);
	
	
	/**
	 * Select all profiles by keywords
	 * @param Keywords
	 * @return
	 */
	@Query("FROM ProfileEntity p where (CONCAT(LOWER(p.lastName),' ',LOWER(p.firstName)) LIKE CONCAT('%',LOWER(:fullName),'%')) OR (CONCAT(LOWER(p.firstName),' ',LOWER(p.lastName)) LIKE CONCAT('%',LOWER(:fullName),'%'))")
	List<ProfileEntity> searchByFullName(@Param("fullName") String fullName, Pageable pageable);
	
}
