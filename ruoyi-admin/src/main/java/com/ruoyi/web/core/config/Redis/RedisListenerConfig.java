package com.ruoyi.web.core.config.Redis;

import com.ruoyi.RuoYiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Configuration
@Import( value = RuoYiApplication.class )
public class RedisListenerConfig {
    @Autowired
    private RedisKeyExpirationListener redisKeyExpirationListener;

    @Bean
    RedisMessageListenerContainer container(@Autowired RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisKeyExpirationListener, new ChannelTopic("__keyevent@0__:expired"));
        container.addMessageListener(redisKeyExpirationListener, new PatternTopic("hkd_*"));
        return container;
    }
}
