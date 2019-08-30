///**
// * <p>Description: []</p>
// *
// * @ClassName RedisSentinelConfig
// * Created on 2018/12/5.
// * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
// * @version 1.0
// * 北京柯莱特科技有限公司 易用云
// */
//package com.camelot.order.config;
//
//
//import com.netflix.discovery.converters.Auto;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;`
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.*;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisSentinelConnection;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisSentinelPool;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * <p>Description: [redis 客户端哨兵模式配置]</p>
// * @ClassName RedisSentinelConfig
// * Created on 2018/12/4.
// *
// * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
// * @version 1.0
// *   北京柯莱特科技有限公司 易用云
// */
//@Configuration
//public class RedisSentinelConfig {
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String masterName;
//    @Value("${spring.redis.sentinel.nodes}")
//    private String sentinelNodes;
//
//    @Value("${spring.redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.pool.min-idle}")
//    private int minIdle;
//
//    @Value("${spring.redis.pool.max-wait}")
//    private long maxWaitMillis;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.timeout}")
//    private Integer timeout;
//
//    @Value("${spring.redis.clientName}")
//    private String clientName;
//
//
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration() {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        for(String node : sentinelNodes.split(",")){
//            String[] item = node.split(":");
//            String ip = item[0];
//            String port = item[1];
//            redisSentinelConfiguration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
//        }
//        redisSentinelConfiguration.setMaster(masterName);
//        redisSentinelConfiguration.setDatabase(database);
//        redisSentinelConfiguration.setPassword(RedisPassword.of(password));
//        return redisSentinelConfiguration;
//    }
//
//    public JedisPoolConfig jedisPoolConfig(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        return jedisPoolConfig;
//    }
//
//    /**
//     * 连接redis的工厂类
//     *
//     * @return
//     */
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        //构造方法中注入RedisSentinelConfiguration对象
//        JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration(), jedisPoolConfig());
//        return factory;
//    }
//
//
//
//    /**
//     * 实例化 RedisTemplate 对象
//     *
//     * @return
//     */
//    @Bean("redisTemplate")
//    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        initRedisTemplate(redisTemplate, redisConnectionFactory);
//        return redisTemplate;
//    }
//    /**
//     * 设置数据存入 redis 的序列化方式,并开启事务
//     *
//     * @param redisTemplate
//     * @param factory
//     */
//    private void initRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
//        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        // 开启事务
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.setConnectionFactory(factory);
//    }
//
//    public String getMasterName() {
//        return masterName;
//    }
//
//    public Set<String> getSentinelNodes() {
//        Set<String> nodes = new HashSet<>();
//        if (null != sentinelNodes && sentinelNodes.length() > 0){
//            for (String str : sentinelNodes.split(",")){
//                nodes.add(str);
//            }
//        }
//        return nodes;
//    }
//
//    public int getMaxIdle() {
//        return maxIdle;
//    }
//
//    public long getMaxWaitMillis() {
//        return maxWaitMillis;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//}