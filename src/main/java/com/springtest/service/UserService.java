package com.springtest.service;


import com.springtest.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);

    User findByUserName(String username);

    String encode(String pass);

    List<User> getActiveUsers();

    List<User> getUsersLikeUsername(String username);

    void authWithVk(HttpServletRequest request, HttpServletResponse response, String code) throws IOException;


}
