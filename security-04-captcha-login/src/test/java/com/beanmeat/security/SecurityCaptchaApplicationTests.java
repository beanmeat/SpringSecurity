package com.beanmeat.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityCaptchaApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		String password = "aaa111";
		String encodePassword = passwordEncoder.encode(password);// 加密方法

		boolean matches = passwordEncoder.matches(password, encodePassword);// 密码匹配
		System.out.println(matches);
	}

}
