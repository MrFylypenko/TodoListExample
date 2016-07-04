package com.springtest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springtest.dao.UserDao;
import com.springtest.dao.UserRoleDao;
import com.springtest.model.entity.User;
import com.springtest.model.entity.UserRole;
import com.springtest.settings.HibernateAwareObjectMapper;
import com.springtest.settings.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	private final String USER_AGENT = "Mozilla/5.0";
	private String redirect_uri = "http://192.168.50.124:8080/vk";


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
		userRole.setRole("ROLE_USER");
		userDao.addUser(user);
		userRole.setUser(user);
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




	@Override
	public void authWithVk(String code) throws IOException {

		String url = "https://oauth.vk.com/access_token?client_id=5181063&client_secret=sDAvKlcVb54De5ffT9ho&redirect_uri="
				+ redirect_uri + "&code=" + code ;

		URL obj = new URL(url);
		HttpsURLConnection con= (HttpsURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		ObjectMapper mapper = new HibernateAwareObjectMapper();


		JsonNode jsonObj = mapper.readTree(response.toString());

		//ObjectNode jsonObj = new ObjectNode (response.toString());

		  //jsonObj = new JSONObject(response.toString());

		final String userId = jsonObj.path("user_id").asText();
		String token = jsonObj.path("access_token").asText();


		User user = null;

		if (user == null) {
			user = new User();
			user.setUsername(userId);
			user.setPassword("ddddd");

			Set<UserRole> userRoles = new HashSet<UserRole>();
			UserRole userRole = new UserRole();
			userRole.setRole("ROLE_USER");
			userRoles.add(userRole);
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Authentication trustedAuthentication = new UserAuthentication(user, authentication.getDetails());

		SecurityContextHolder.getContext().setAuthentication(trustedAuthentication);


	}

}
