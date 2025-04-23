package com.beanmeat.security.handler;

import cn.hutool.json.JSONUtil;
import com.beanmeat.security.result.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-23
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        R result = R.builder().code(200).msg("登录成功").info(authentication).build();
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
