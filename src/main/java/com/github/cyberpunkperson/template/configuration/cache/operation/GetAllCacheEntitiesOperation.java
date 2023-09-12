package com.github.cyberpunkperson.template.configuration.cache.operation;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.cyberpunkperson.essentials.error.core.exception.ExceptionFactory;
import com.github.cyberpunkperson.template.cache.model.Entity;
import com.github.cyberpunkperson.template.configuration.cache.NamedCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.cyberpunkperson.template.error.BusinessErrorCode.CACHE_NOT_FOUND;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class GetAllCacheEntitiesOperation {

    private final Map<String, Cache<?, ?>> cacheMap;
    private final ExceptionFactory exceptionFactory;

    public GetAllCacheEntitiesOperation(List<NamedCache> caches, ExceptionFactory exceptionFactory) {
        this.cacheMap = caches.stream().collect(toMap(NamedCache::name, NamedCache::cache));
        this.exceptionFactory = exceptionFactory;
    }

    public List<Entity> activate(String cacheName) {
        return ofNullable(cacheMap.get(cacheName))
                .map(cache -> {
                    var innerCache = cache.asMap();
                    List<Entity> entities = new ArrayList<>(innerCache.size());
                    innerCache.forEach((key, value) -> entities.add(convertToCacheEntity(key, value)));
                    return entities;
                })
                .orElseThrow(() -> {
                    throw exceptionFactory.builder(CACHE_NOT_FOUND)
                            .arg("cacheName", cacheName)
                            .build();
                });
    }

    private <K, V> Entity convertToCacheEntity(K key, V value) {
        return new Entity().key(key).value(value);
    }
}
