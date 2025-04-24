package com.beanmeat.security.handler;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.beanmeat.security.constant.Constant;
import com.beanmeat.security.entity.TUser;
import com.beanmeat.security.result.R;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author tchstart
 * @data 2025-04-23
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 生成JWT(token)
        TUser tUser = (TUser) authentication.getPrincipal();
        String userJson = JSONUtil.toJsonStr(tUser);
        String token = JWTUtil.createToken(Map.of("user", userJson), Constant.SECRET.getBytes());
        // 写入Redis
        redisTemplate.opsForValue(); // 操作string
        redisTemplate.opsForHash(); // 操作hash
        redisTemplate.opsForList(); // 操作list
        redisTemplate.opsForSet();  // 操作set
        redisTemplate.opsForZSet(); // 操作zset
        redisTemplate.opsForHash().put(Constant.REDIS_TOKEN_KEY,String.valueOf(tUser.getId()),token);

        R result = R.builder().code(200).msg("登录成功").info(token).build();
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
