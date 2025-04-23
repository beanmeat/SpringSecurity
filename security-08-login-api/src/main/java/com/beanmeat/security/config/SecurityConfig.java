package com.beanmeat.security.config;

import com.beanmeat.security.filter.CaptchaFilter;
import com.beanmeat.security.handler.AppLogoutSuccessHandler;
import com.beanmeat.security.handler.MyAuthenticationFailureHandler;
import com.beanmeat.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author tchstart
 * @data 2025-04-18
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AppLogoutSuccessHandler appLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置跨域
    @Bean
    public CorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        // 跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // 允许任何的请求头
        corsConfiguration.setAllowedMethods(Arrays.asList("*")); // 允许任何请求方法，post，get，put，delete
        corsConfiguration.setAllowedOrigins(Arrays.asList("*")); // 允许任何来源, http://localhost:8081

        // 对于后端任何接口，都采用corsConfiguration配置进行跨域访问
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return configurationSource;
    }

    /**
     * 配置spring security框架的一些行为（配置我们自己的登录页，不使用框架默认的登录页）
     * 当你配置了SecurityFilterChain这个Bean之后，SpringSecurity框架的某些默认行为就认为弄丢了，此时你需要加回来
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,CorsConfigurationSource configurationSource) throws Exception { // httpSecurity是方法参数注入Bean
        return httpSecurity
                // 自定义登录页
                .formLogin( formLogin -> {
                    formLogin.loginProcessingUrl("/user/login") // 登录的账号密码往那个地址提交
                    // 在访问/user/login地址之前，对于后端应用来说，没有上一个地址，那默认是跳转到项目的跟路径/

                    .successHandler(myAuthenticationSuccessHandler) // 登录成功执行这个handler
                    .failureHandler(authenticationFailureHandler); // 登录失败执行这个handler
                })

                .logout( logout -> {
                    logout.logoutUrl("/user/logout") // 退出接口请求提交到哪个地址
                            .logoutSuccessHandler(appLogoutSuccessHandler); // 退出成功后执行该handler
                })

                // 把所有接口都会进行登录状态检查的默认行为再加回来
                .authorizeHttpRequests(request -> {
                    request.anyRequest().authenticated(); // 除了上面的特殊情况外，任何对后端接口的请求，都需要登录后才能访问
                })
                .csrf( csrf -> {
                    // 禁用csrf跨站请求伪造，（解决前端提交要携带一个token的问题）
                    csrf.disable();
                })
                .cors(cors -> { // 允许前端跨域访问
                    cors.configurationSource(configurationSource);
                })

                .sessionManagement( sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



}
