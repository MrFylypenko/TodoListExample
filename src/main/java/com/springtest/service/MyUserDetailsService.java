package com.springtest.service;

import com.springtest.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		UserDetails user = userDao.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

}