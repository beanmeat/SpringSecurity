package com.beanmeat.security.handler;

import cn.hutool.json.JSONUtil;
import com.beanmeat.security.result.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-24
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 权限不足
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        R result = R.builder().code(401).msg("权限不足").build();
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
