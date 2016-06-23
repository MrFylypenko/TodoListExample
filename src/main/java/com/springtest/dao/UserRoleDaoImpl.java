package com.springtest.dao;

import com.springtest.model.entity.UserRole;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRoleDaoImpl implements UserRoleDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void addUserRole(UserRole userRole) {
		entityManager.persist(userRole);
	}
}
