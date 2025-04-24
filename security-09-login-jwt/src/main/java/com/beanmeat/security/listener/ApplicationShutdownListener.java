package com.beanmeat.security.listener;

import com.beanmeat.security.constant.Constant;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author tchstart
 * @data 2025-04-24
 */
@Component
public class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * spring context 关闭时，会触发该方法
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("spring context 关闭了...");
        // 让登录用户的token失效，就是把redis里面的token删除

        redisTemplate.delete(Constant.REDIS_TOKEN_KEY);
    }
}
