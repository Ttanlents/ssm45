package com.yjf.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/**
 * spring整合缓存技术：
 * 1.spring data redis整合redis作为缓存
 * 2.spring cache 操作缓存
 * a.声明缓存管理器对象CacheManager -创建缓存对象，自动管理缓存对象
 * b.开启缓存注解支持
 * c.需要放入缓存的对象类型，需要实现序列化接口
 * d.在需要管理缓存的服务层逻辑方法上，添加缓存注解
 */
@Configuration
@PropertySource(value = "classpath:redis.properties",encoding = "utf-8")
@EnableCaching  //开启缓存注解支持
public class SpringCacheConfig {

    /**
     * redis的连接工厂接口RedisConnectionFactory，创建redis连接和维护连接池
     * @return
     */
    @Bean
    public RedisConnectionFactory getRedisConnection(@Value("${spring.redis.host}") String host,
                                                     @Value("${spring.redis.port}") int port,
                                                     @Value("${spring.redis.password}") String password,
                                                     @Value("${spring.redis.pool.max-idle}")int minIdle){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        //设置池对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        jedisConnectionFactory.setPoolConfig(config);

        return jedisConnectionFactory;
    }

    //声明bean的名字为redisTemplate
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);//设置连接工厂对象
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);//设置key的序列化器，使用String序列化器，默认以UTF-8对key进行编码和解码
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        //设置value的序列化器，该序列化器会自动将对象类型作为其中转换的一个key和value存入json中
        redisTemplate.setValueSerializer(redisSerializer);

        //设置hash类型
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }


    //创建spring缓存管理器
    @Bean
    public RedisCacheManager getCacheManager(RedisTemplate redisTemplate){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(120);//设置默认过时时间

        //声明管理的缓存对象名字   会自动创建该名字的缓存对象
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("officeCache");
        redisCacheManager.setCacheNames(Arrays.asList(new String[]{"officeCache"}));
        return redisCacheManager;
    }


}
