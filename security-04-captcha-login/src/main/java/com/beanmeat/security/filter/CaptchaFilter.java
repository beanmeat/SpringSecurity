package com.beanmeat.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-21
 * 接收前端的验证码，并对验证码进行验证
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {// 在Spring框架中，实现Filter，直接继承OnePerRequestFilter更方便

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String code = request.getParameter("captcha");
        String sessionCode = (String) request.getSession().getAttribute("captcha");

        String requestURI = request.getRequestURI();

        if(requestURI.equals("/user/login")){ // 如果是登录请求，就验证验证码，否则不需要验证
            if(!StringUtils.hasText(code)){
                // 验证没通过
                response.sendRedirect("/");
            }else if(!code.equalsIgnoreCase(sessionCode)){
                response.sendRedirect("/");
            }else{
                filterChain.doFilter(request,response);
            }
        }else{
            // 不是登录请求，直接放行
            filterChain.doFilter(request,response);
        }
    }

}
