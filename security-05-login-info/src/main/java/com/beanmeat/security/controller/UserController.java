package com.beanmeat.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author tchstart
 * @data 2025-04-21
 */
@Controller
public class UserController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, StringSecurity!";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/welcome")
    @ResponseBody
    public Object welcome(Principal principal) {
//        return SecurityContextHolder.getContext().getAuthentication();
        // Principal的实现类都可以
        return principal;
    }
}
