package com.springtest.comtroller;

import com.springtest.model.entity.User;
import com.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vano on 19.02.16.
 */

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public Object login2(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Object users() {
        return userService.getActiveUsers();
    }


    @RequestMapping(value = "/query/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Object likeUsername(@PathVariable String username) {
        return userService.getUsersLikeUsername(username);
    }


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object likeUsernameQuery() {
        return userService.getUsersLikeUsername("");
    }



}
