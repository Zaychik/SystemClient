package com.zaychik.learning.system_user_rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(environment.getProperty("cache.name"),
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(Long.parseLong(environment.getProperty("cache.duration.sec")))));
    }
}
