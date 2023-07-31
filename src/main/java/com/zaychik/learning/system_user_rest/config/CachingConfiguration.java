package com.zaychik.learning.system_user_rest.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "cache")
@EnableCaching
@Data
public class CachingConfiguration {
    private String name;
    private Long duration;

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(name,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(duration)));
    }
}
