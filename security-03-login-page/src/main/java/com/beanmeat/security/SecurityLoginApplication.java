package com.beanmeat.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.beanmeat.security.mapper")
@SpringBootApplication
public class SecurityLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityLoginApplication.class, args);
	}

}
