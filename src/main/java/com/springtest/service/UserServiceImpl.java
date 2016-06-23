package com.springtest.service;

import com.springtest.dao.UserDao;
import com.springtest.dao.UserRoleDao;
import com.springtest.model.entity.User;
import com.springtest.model.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	@Transactional
	public void addUser(User user) {
		UserRole userRole = new UserRole();
		userRole.role = "ROLE_USER";
		userDao.addUser(user);
		userRole.user = user;
		userRoleDao.addUserRole(userRole);
	}

	@Override
	@Transactional
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public String encode(String pass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode(pass);
		return result;
	}

	@Override
	public List<User> getActiveUsers() {
		final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		List<User> users = new ArrayList<User>();

		for(final Object principal : allPrincipals) {
			if(principal instanceof User) {
				final User user = (User) principal;
				users.add(user);
			}
		}

		return users;
	}

	@Override
	public List<User> getUsersLikeUsername(String username) {
		return userDao.getUsersLikeUsername(username);
	}

}
