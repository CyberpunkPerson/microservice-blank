package com.github.cyberpunkperson.template.configuration.cache.operation;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.cyberpunkperson.essentials.error.core.exception.ExceptionFactory;
import com.github.cyberpunkperson.template.configuration.cache.NamedCache;

import java.util.List;
import java.util.Map;

import static com.github.cyberpunkperson.template.error.BusinessErrorCode.CACHE_NOT_FOUND;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class EvictAllCacheEntitiesOperation {

    private final Map<String, Cache<?, ?>> cacheMap;
    private final ExceptionFactory exceptionFactory;

    public EvictAllCacheEntitiesOperation(List<NamedCache> caches, ExceptionFactory exceptionFactory) {
        this.cacheMap = caches.stream().collect(toMap(NamedCache::name, NamedCache::cache));
        this.exceptionFactory = exceptionFactory;
    }

    public void activate(String cacheName) {
        ofNullable(cacheMap.get(cacheName))
                .ifPresentOrElse(Cache::invalidateAll,
                        () -> {
                            throw exceptionFactory.builder(CACHE_NOT_FOUND)
                                    .arg("cacheName", cacheName)
                                    .build();
                        });
    }
}
