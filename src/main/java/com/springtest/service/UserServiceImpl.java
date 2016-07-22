package com.springtest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtest.dao.AuthDao;
import com.springtest.dao.UserDao;
import com.springtest.dao.UserRoleDao;
import com.springtest.model.auth.AuthorityType;
import com.springtest.model.auth.VkAuthUser;
import com.springtest.model.entity.User;
import com.springtest.model.entity.UserRole;
import com.springtest.settings.HibernateAwareObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final String USER_AGENT = "Mozilla/5.0";
    private String redirect_uri = "http://tasks.konekon.ru/vk";

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    AuthDao authDao;

    @Autowired
    @Qualifier("sas")
    CompositeSessionAuthenticationStrategy concurrentSessionControlStrategy;


    @Autowired
    @Qualifier("sessionRegistry")
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

        //sessionRegistry.getAllSessions(allPrincipals.get(0),false).get(0).expireNow();


        List<User> users = new ArrayList<User>();
        System.out.println("allPrincipals.size = " + allPrincipals.size());

        for (final Object principal : sessionRegistry.getAllPrincipals()) {
            final User user = (User) principal;
            users.add(user);
        }

        return users;
    }

    @Override
    public List<User> getUsersLikeUsername(String username) {
        return userDao.getUsersLikeUsername(username);
    }


    @Transactional
    @Override
    public void authWithVk(HttpServletRequest request, HttpServletResponse response, String code) throws IOException {

        String url = "https://oauth.vk.com/access_token?client_id=5181063&client_secret=sDAvKlcVb54De5ffT9ho&redirect_uri="
                + redirect_uri + "&code=" + code;

        String result = sendQueryByUrl(url);

        ObjectMapper mapper = new HibernateAwareObjectMapper();
        JsonNode jsonObj = mapper.readTree(result);
        final String vkId = jsonObj.path("user_id").asText();
        String accessToken = jsonObj.path("access_token").asText();

        VkAuthUser authUser = authDao.getVkAuthUserByVkId(vkId);

        if (authUser == null) {

            User user = new User();
            user.setUsername(vkId);

            result = sendQueryByUrl("https://api.vk.com/method/getProfiles?uid=" +
                    vkId + "&access_token=" + accessToken + "&fields=photo_200,city,verified,country");

            jsonObj = mapper.readTree(result).path("response");

            user.setFirstName(jsonObj.get(0).path("first_name").asText());
            user.setLastName(jsonObj.get(0).path("last_name").asText());
            user.setImageUrl(jsonObj.get(0).path("photo_200").asText());


            userDao.addUser(user);

            UserRole userRole = new UserRole();
            userRole.setRole("ROLE_USER");
            userRole.setUser(user);

            userRoleDao.addUserRole(userRole);

            Set<UserRole> userRoles = new HashSet<UserRole>();
            userRoles.add(userRole);
            user.setRoles(userRoles);

            authUser = new VkAuthUser();
            authUser.setUser(user);
            authUser.setType(AuthorityType.VK);
            authUser.setVkId(vkId);
            authUser.setAccessToken(accessToken);

            authDao.saveAuth(authUser);
        }

        //if token changed
        authUser.setAccessToken(accessToken);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        authUser.setDetails(authentication.getDetails());

        SecurityContextHolder.getContext().setAuthentication(authUser);

        concurrentSessionControlStrategy.onAuthentication(authUser, request, response);


    }


    private String sendQueryByUrl(String url) throws IOException {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }


}
