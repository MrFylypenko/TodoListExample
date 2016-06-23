package com.springtest.dao;


import com.springtest.model.entity.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    List<User> getAllUsers();

    User findByUserName(String username);

    List<User> getUsersLikeUsername(String username);


}
