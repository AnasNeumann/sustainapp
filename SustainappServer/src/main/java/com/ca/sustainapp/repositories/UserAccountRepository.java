package com.ca.sustainapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.sustainapp.entities.UserAccountEntity;

/**
 * Repository for users accounts
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 12/02/2017
 * @version 1.0
 */
@Repository
public interface UserAccountRepository extends JpaSpecificationExecutor<UserAccountEntity>, JpaRepository<UserAccountEntity, Long> {

	/**
	 * Count Entities by Id
	 * @param id
	 * @return total by id
	 */
	@Query("SELECT COUNT(*) FROM UserAccountEntity AS u WHERE u.id = :id")
	Integer countById(@Param("id") Long id); 
	
	/**
	 * Get by mail and password
	 * @param mail
	 * @param password
	 * @return
	 */
	@Query("FROM UserAccountEntity AS u WHERE u.mail = :mail AND u.password = :password")
	List<UserAccountEntity> getByLoginAndPassword(@Param("mail") String mail, @Param("password") String password);
	
	/**
	 * Get by token
	 * @param token
	 * @return
	 */
	@Query("FROM UserAccountEntity AS u WHERE u.token = :token")
	List<UserAccountEntity> getByToken(@Param("token") String token);
	
	/**
	 * delete entities by id
	 * @param id
	 */
	@Modifying
	@Query("DELETE FROM UserAccountEntity u WHERE u.id = :id")
	void delete(@Param("id") Long id);
}
