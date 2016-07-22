package com.springtest.controller;

import com.springtest.model.entity.User;
import com.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HelloController {


    @Autowired
    UserService userService;

    @Order(10)
    @RequestMapping(value = "/**",method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    @RequestMapping(value = "403")
    public String page403(HttpServletResponse response) {
        response.setStatus(403);
        return "403";
    }

    @RequestMapping(value = "401")
    public String page401(HttpServletResponse response) {
        response.setStatus(401);
        return "401";
    }




    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public Object login2(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }







    @RequestMapping(value = "/login/getuser", method = RequestMethod.GET)
    @ResponseBody
    public Object login2(HttpServletRequest request, HttpServletResponse response) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final String name = auth.getName();
        User user = null;

        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            System.out.println("Ошибка, когда юзер не авторизирован, все нормально!"); //todo
        }

        if (name.equals("anonymousUser")) {
            response.setStatus(403);
            return new User();
        }

        response.setStatus(200);
        return user;
    }


    @Order(9)
    @RequestMapping(value = "vk", method = RequestMethod.GET, params = {"code"})
    public String vk(@RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception {

        userService.authWithVk(request, response, code);

        return "index";
    }


}


