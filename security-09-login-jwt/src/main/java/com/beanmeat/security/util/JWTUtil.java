package com.beanmeat.security.util;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beanmeat.security.entity.TUser;

import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    // 密钥不能让别人知道，这个密钥放在服务器上
    public static final String secret = "asdhfuoiwebufbusbcxiuabsduifhwedhr";

    public static void main(String[] args) {
        TUser tUser = new TUser();
        tUser.setId(123123);
        tUser.setLoginAct("cat");
        tUser.setEmail("cat@163.com");
        String userJson = JSONUtil.toJsonStr(tUser);

        // 生成jwt字符串
        String token = JWTUtil.createToken(userJson);
        System.out.println(token);

        Boolean verified = verifyToken(token);
        System.out.println("验证结果：" + verified);
    }

    // 怎末生成JWT
    public static String createToken(String userJson){
        // 组装头部数据
        Map<String,Object> header = new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");

        return JWT.create()
                // 头部
                .withHeader(header)
                // 负载
                .withClaim("user",userJson)
                .withClaim("phone","13117974652")
                // 签名
                .sign(Algorithm.HMAC256(secret));
    }

    // 怎末验证JWT有没有被篡改
    public static Boolean verifyToken(String token){
        try{
            // 使用密钥创建一个解析对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            // 验证JWT
            jwtVerifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // 怎末解析出JWT里面的负载数据
    public static String parseToken(String token){
        try{
            // 使用密钥创建一个jwt验证器对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            // 验证JWT，得到一个解码后的jwt对象
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            // 通过解码后的jwt对象获取负载的数据
            Claim user = decodedJWT.getClaim("user");
            System.out.println(user.asString());

            Claim phone = decodedJWT.getClaim("phone");
            System.out.println(phone.asString());
            return user.asString();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
