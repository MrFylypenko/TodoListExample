package com.springtest.comtroller;

import com.springtest.model.entity.User;
import com.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HelloController {


    @Autowired
    UserService userService;

    @Order(2)
    @RequestMapping(value = "/**",method = RequestMethod.GET)
    public String index() {
        return "index";
    }

   /* @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome() {
        return "hello";
    }

    @RequestMapping(value = "file*", method = RequestMethod.GET)
    public String file() {
        return "index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "index";
    }

    @RequestMapping(value = "task", method = RequestMethod.GET)
    public String task() {
        return "index";
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about() {
        return "index";
    }

    @RequestMapping(value = "mytasks", method = RequestMethod.GET)
    public String mytasks() {
        return "index";
    }

    @RequestMapping(value = "projects", method = RequestMethod.GET)
    public String myProjects() {
        return "index";
    }*/


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error",
                    getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("index");
        return model;
    }

    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession()
                .getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
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


    @RequestMapping(value = "/vk", method = RequestMethod.GET, params = {"code"})
    public String vk(@RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception {


        userService.authWithVk(request, response, code);

        return "index";
    }


}


