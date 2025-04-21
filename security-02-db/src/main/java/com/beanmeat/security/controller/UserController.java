package com.beanmeat.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tchstart
 * @data 2025-04-21
 */
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, StringSecurity!";
    }
}
