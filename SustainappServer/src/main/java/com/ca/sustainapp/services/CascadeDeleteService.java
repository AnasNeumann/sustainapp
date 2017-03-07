package com.ca.sustainapp.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.ca.sustainapp.entities.TeamEntity;
import com.ca.sustainapp.entities.TeamRoleEntity;
import com.ca.sustainapp.repositories.TeamRepository;
import com.ca.sustainapp.repositories.TeamRoleRepository;

/**
 * Service pour la suppression en cascade
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2107
 * @verion 1.0
 */
@Service("deleteService")
public class CascadeDeleteService {

	/**
	 * Le repository
	 */
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamRoleRepository roleRepository;
	
	/**
	 * cascade delete a team
	 * @param team
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamEntity team){
		for(TeamRoleEntity role : team.getListRole()){
			cascadeDelete(role);
		}
		teamRepository.delete(team.getId());
	}

	/**
	 * cascade delete a teamRole
	 * @param team
	 */
	@Modifying
	@Transactional
	public void cascadeDelete(TeamRoleEntity role){
		roleRepository.delete(role.getId());
	}

}