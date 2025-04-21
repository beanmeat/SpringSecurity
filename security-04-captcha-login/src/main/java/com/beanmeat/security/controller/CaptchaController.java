package com.beanmeat.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-21
 */
@Controller
public class CaptchaController {

    @GetMapping("/common/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 告诉浏览器，响应类型是图片，是jpeg格式
        response.setContentType("image/jpeg");

        // 生成的是一个验证码，无需跳转，以IO流的方式写出去
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 20, 4, 10, 1);

        request.getSession().setAttribute("captcha",captcha.getCode());

        captcha.write(response.getOutputStream());
    }
}
