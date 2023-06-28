package com.zaychik.learning.system_user_rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfiguration {

    @Value("${jedis.host}")
    private String host;
    @Value("${jedis.port}")
    private int port;
    @Value("${cache.name}")
    private String cacheName;
    @Value("${cache.duration.sec}")
    private int cacheDuration;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(host);
        jedisConFactory.setPort(port);
        return jedisConFactory;
    }
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(cacheName,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(cacheDuration)));
    }
}
