package com.beanmeat.security.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.beanmeat.security.constant.Constant;
import com.beanmeat.security.entity.TUser;
import com.beanmeat.security.result.R;


import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author tchstart
 * @data 2025-04-24
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // 登录接口，不需要验证token（登录时，还没有生成token）
        String requestURI = request.getRequestURI(); // /user/login
        if(requestURI.equals("/user/login")){
            filterChain.doFilter(request,response);
        }else{
            String token = request.getHeader("token"); // 从请求头中获取token值
            if(!StringUtils.hasText(token)){
                R ret = R.builder().code(901).msg("请求Token为空").build();
                response.getWriter().write(JSONUtil.toJsonStr(ret));
            }else{
                boolean verify = false;
                try{
                    verify = JWTUtil.verify(token, Constant.SECRET.getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(!verify){
                    R ret = R.builder().code(901).msg("请求Token不合法").build();
                    response.getWriter().write(JSONUtil.toJsonStr(ret));
                }else{
                    JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
                    String userJSON = payloads.get("user", String.class);
                    TUser tUser = JSONUtil.toBean(userJSON,TUser.class);
                    // 拿redis的token
                    String redisToken = (String) redisTemplate.opsForHash().get(Constant.REDIS_TOKEN_KEY,String.valueOf(tUser.getId()));
                    if(!token.equals(redisToken)){
                        R ret = R.builder().code(903).msg("请求Token错误").build();
                        response.getWriter().write(JSONUtil.toJsonStr(ret));
                    }else{
                        // 验证通过
                        // 要在spring security上下文中放置一个认证对象，这样spring security在执行后续的Filter的时候，才知道这个人是登录了的
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tUser,null, tUser.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        // 放行
                        filterChain.doFilter(request,response);
                    }
                }
            }
        }

    }
}
