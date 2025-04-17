# Spring Security

Spring Security is a framework that provides [authentication](https://docs.spring.io/spring-security/reference/6.3/features/authentication/index.html), [authorization](https://docs.spring.io/spring-security/reference/6.3/features/authorization/index.html), and [protection against common attacks](https://docs.spring.io/spring-security/reference/6.3/features/exploits/index.html). With first class support for securing both [imperative](https://docs.spring.io/spring-security/reference/6.3/servlet/index.html) and [reactive](https://docs.spring.io/spring-security/reference/6.3/reactive/index.html) applications, it is the de-facto standard for securing Spring-based applications.

> SpringSecurity 是一个提供身份验证、授权和防止常见攻击的框架。通过对保护命令式和反应式应用程序的一流支持，它成为保护基于 Spring 的应用程序的事实标准。

官网：https://spring.io/projects/spring-security

## Getting Started

链接：https://docs.spring.io/spring-security/reference/servlet/getting-started.html

**更新依赖项**

首先需要将 SpringSecurity 添加到应用程序的类路径中；有两种方法可以实现这一点： 使用 Maven 或 Gradle。

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

在类路径上启用了 Spring Security 后，您现在可以运行 Spring Boot 应用程序了。以下片段显示了一些输出，表明您的应用程序中已启用 Spring Security:

```bash
$ ./mvnw spring-boot:run
...
INFO 23689 --- [  restartedMain] .s.s.UserDetailsServiceAutoConfiguration :

Using generated security password: 8e557245-73e2-4286-969a-ff57fe326336

...
```

现在您已经运行了它，您可以尝试命中一个端点，看看会发生什么。如果您在没有凭据的情况下访问端点，如下所示：

```bash
$ curl -i http://localhost:8080/some/path
HTTP/1.1 401
...
```

那么 Spring Security 拒绝使用未经授权的 401 访问。

>如果您在浏览器中提供相同的 URL，它将重定向到默认登录页面。

使用凭证进行查询

```java
$ curl -i -u user:8e557245-73e2-4286-969a-ff57fe326336 http://localhost:8080/some/path
HTTP/1.1 404
...
```

然后 Spring Boot 将为请求提供服务，在这种情况下返回一个 404 Not Found，因为 /some/path 不存在。



*Spring Boot Security Auto Configuration*

```java
@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String generatedPassword = // ...;
        return new InMemoryUserDetailsManager(User.withUsername("user")
                .password(generatedPassword).roles("USER").build());
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
        return new DefaultAuthenticationEventPublisher(delegate);
    }
}
```

1. Adds the `@EnableWebSecurity` annotation. (Among other things, this publishes [Spring Security’s default `Filter` chain](https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-securityfilterchain) as a `@Bean`)
2. Publishes a [`UserDetailsService`](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html) `@Bean` with a username of `user` and a randomly generated password that is logged to the console
3. Publishes an [`AuthenticationEventPublisher`](https://docs.spring.io/spring-security/reference/servlet/authentication/events.html) `@Bean` for publishing authentication events

>Spring Boot 将任何发布为 @Bean 的 Filter 添加到应用程序的筛选器链中。这意味着结合使用 @EnableWebSecurity 和 Spring Boot 会自动为每个请求注册 Spring Security 的过滤器链。

## Architecture

讨论基于 Servlet 的应用程序中 Spring Security 的高级体系结构。我们将在参考文献的身份验证、授权和防止利用部分中构建这种高层次的理解。

**A Review of Filters**

Spring Security’s Servlet support is based on Servlet Filters, so it is helpful to look at the role of Filters generally first. The following image shows the typical layering of the handlers for a single HTTP request.

![image-20250417185304274](images/image-20250417185304274.png)

客户机向应用程序发送一个请求，容器创建一个 FilterChain，其中包含 Filter 实例和 Servlet，它们应该根据请求 URI 的路径处理 HttpServletRequest。在 Spring MVC 应用程序中，Servlet 是 DispatcherServlet 的一个实例。一个 Servlet 最多只能处理一个 HttpServletRequest 和 HttpServletResponse。但是，可以使用多个过滤器：

- 防止下游过滤器实例或 Servlet 被调用。在这种情况下，Filter 通常会写入 HttpServletResponse。
- 修改下游过滤器实例和 Servlet 使用的 HttpServletRequest 或 HttpServletResponse。

过滤器的力量来自于传递给它的过滤器链。

```java
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
	// do something before the rest of the application
    chain.doFilter(request, response); // invoke the rest of the application
    // do something after the rest of the application
}
```

由于 Filter 只影响下游 Filter 实例和 Servlet，因此调用每个 Filter 的顺序非常重要。