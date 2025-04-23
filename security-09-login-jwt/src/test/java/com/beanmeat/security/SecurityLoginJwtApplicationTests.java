package com.beanmeat.security;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SecurityLoginJwtApplicationTests {

    // 密钥
    public static final String secret = "asdhfuoiwebufbusbcxiuabsduifhwedhr";

    @Test
    void contextLoads() {
        // 1.生成jwt
        Map<String,Object> payLoad = new HashMap<>();
        payLoad.put("id",123);
        payLoad.put("phone","13117974652");
        String jwt = JWTUtil.createToken(payLoad, secret.getBytes(StandardCharsets.UTF_8));
        System.out.println(jwt);

        String jwt2 = JWTUtil.createToken(payLoad, JWTSignerUtil.hs256(secret.getBytes(StandardCharsets.UTF_8)));
        System.out.println(jwt2);

        // 验证jwt
        boolean verify = JWTUtil.verify(jwt, secret.getBytes(StandardCharsets.UTF_8));
        System.out.println(verify);
        
        // 解析jwt
        JWT parseJWT = JWTUtil.parseToken(jwt);
        JWTPayload payload = parseJWT.getPayload();
        Object id = payLoad.get("id");
        Object phone = payLoad.get("phone");
        System.out.println(id + "----" + phone);
    }

}
