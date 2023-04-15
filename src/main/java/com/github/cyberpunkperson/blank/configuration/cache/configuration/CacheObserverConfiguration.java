package com.github.cyberpunkperson.blank.configuration.cache.configuration;

import com.github.cyberpunkperson.blank.configuration.cache.NamedCache;
import com.github.cyberpunkperson.blank.configuration.cache.controller.CacheController;
import com.github.cyberpunkperson.blank.configuration.cache.operation.EvictAllCacheEntitiesOperation;
import com.github.cyberpunkperson.blank.configuration.cache.operation.GetAllCacheEntitiesOperation;
import com.github.cyberpunkperson.essentials.error.core.exception.ExceptionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "cache.endpointEnable", havingValue = "true")
class CacheObserverConfiguration {

    @Bean
    CacheController cacheController(EvictAllCacheEntitiesOperation evictAllCacheEntitiesOperation,
                                    GetAllCacheEntitiesOperation getAllCacheEntitiesOperation) {
        return new CacheController(evictAllCacheEntitiesOperation, getAllCacheEntitiesOperation);
    }

    @Bean
    GetAllCacheEntitiesOperation getAllCacheEntitiesOperation(List<NamedCache> caches, ExceptionFactory exceptionFactory) {
        return new GetAllCacheEntitiesOperation(caches, exceptionFactory);
    }

    @Bean
    EvictAllCacheEntitiesOperation evictAllCacheEntitiesOperation(List<NamedCache> caches, ExceptionFactory exceptionFactory) {
        return new EvictAllCacheEntitiesOperation(caches, exceptionFactory);
    }
}