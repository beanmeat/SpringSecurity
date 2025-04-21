package com.beanmeat.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
