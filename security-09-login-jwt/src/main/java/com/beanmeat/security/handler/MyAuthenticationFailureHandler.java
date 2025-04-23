package com.beanmeat.security.handler;

import cn.hutool.json.JSONUtil;
import com.beanmeat.security.result.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-23
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        R result = R.builder().code(500).msg("登录失败：" + exception.getMessage()).build();
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
