package com.camelot.order.config.redisSetinelconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;


@Configuration
public class SentinelRedisConfig {
    private final Logger logger = LoggerFactory.getLogger(SentinelRedisConfig.class);

    @Autowired
    SentinelConfigurationProperties sentinelConfigurationProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String masterName = sentinelConfigurationProperties.getMaster();
        Set<String> sentinels = sentinelConfigurationProperties.getNodes();
        if (masterName == null || masterName.equals("") || sentinels == null || sentinels.size() == 0) {
            logger.error(String.format("redis配置参数不能正确，情况检查配置：master=%s, sentinels=%s", masterName, sentinels.toString()));
            throw new IllegalArgumentException(String.format("redis配置参数不能正确，情况检查配置：master=%s, sentinels=%s", masterName, sentinels.toString()));
        }

        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(masterName, sentinels);

        return new JedisConnectionFactory(redisSentinelConfiguration);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate =  new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }




}
