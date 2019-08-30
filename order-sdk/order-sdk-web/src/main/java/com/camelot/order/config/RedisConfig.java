//package com.camelot.order.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.*;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//public class RedisConfig {
//
//    @Autowired
//    ClusterConfigurationProperties clusterConfigurationProperties;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisClusterConfiguration redisClusterConfiguration= new RedisClusterConfiguration(clusterConfigurationProperties.getNodes());
//
//        return new JedisConnectionFactory(redisClusterConfiguration);
//    }
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate =  new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
//
//}
