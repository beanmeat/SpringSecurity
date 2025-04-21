package com.beanmeat.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author tchstart
 * @data 2025-04-18
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置spring security框架的一些行为（配置我们自己的登录页，不适用框架默认的登录页）
     * 当你配置了SecurityFilterChain这个Bean之后，SpringSecurity框架的某些默认行为就认为弄丢了，此时你需要加回来
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // httpSecurity是方法参数注入Bean
        return httpSecurity
                // 自定义登录页
                .formLogin( formLogin -> {
                    formLogin.loginProcessingUrl("/user/login") // 登录的账号密码往那个地址提交
                            .loginPage("/toLogin"); // 定制登录页（Thymeleaf页面）
                })

                // 把所有接口都会进行登录状态检查的默认行为再加回来
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/toLogin").permitAll(); // 特殊情况的设置，permitAll允许不登陆就可以访问
                    request.anyRequest().authenticated(); // 除了上面的特殊情况外，任何对后端接口的请求，都需要登录后才能访问
                })
                .build();
    }

}
