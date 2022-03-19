package com.github.cyberpunkperson.microserviceblank.configuration.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.benmanes.caffeine.cache.Caffeine.from;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Configuration
@EnableCaching
class CacheConfiguration {

    @Bean
    CacheManager cacheManager(CacheProperties cacheProperties) {
        var caches = ofNullable(cacheProperties.specifications())
                .map(cacheSpecifications -> cacheSpecifications.stream()
                        .map(specification -> new CaffeineCache(specification.name(), from(specification.spec()).build()))
                        .toList())
                .orElse(emptyList());

        var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
