package com.beanmeat.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.beanmeat.security.mapper")
@SpringBootApplication
public class SecurityLoginInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityLoginInfoApplication.class, args);
	}

}
