package com.github.cyberpunkperson.blank.support.cache.operation;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.cyberpunkperson.blank.support.cache.NamedCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Component
public class EvictAllCacheEntitiesOperation {

    private final Map<String, Cache<?, ?>> cacheMap;
//    private final ExceptionFactory exceptionFactory;

    public EvictAllCacheEntitiesOperation(List<NamedCache> caches) {
        this.cacheMap = caches.stream().collect(toMap(NamedCache::name, NamedCache::cache));
//        this.exceptionFactory = exceptionFactory;
    }

    public void activate(String cacheName) {
        ofNullable(cacheMap.get(cacheName))
                .ifPresentOrElse(Cache::invalidateAll,
                        () -> new IllegalArgumentException("Replace on ExceptionFactory"));
    }
}
