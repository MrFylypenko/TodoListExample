package com.springtest.controller;

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
