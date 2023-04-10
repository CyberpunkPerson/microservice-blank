package com.github.cyberpunkperson.blank.support.cache.operation;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.cyberpunkperson.blank.cache.model.Entity;
import com.github.cyberpunkperson.blank.support.cache.NamedCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Component
public class GetAllCacheEntitiesOperation {

    //    private final ExceptionFactory exceptionFactory;
    private final Map<String, Cache<?, ?>> cacheMap;

    public GetAllCacheEntitiesOperation(List<NamedCache> caches) {
        this.cacheMap = caches.stream().collect(toMap(NamedCache::name, NamedCache::cache));
//        this.exceptionFactory = exceptionFactory;
    }

    public List<Entity> activate(String cacheName) {
        return ofNullable(cacheMap.get(cacheName))
                .map(cache -> {
                    var innerCache = cache.asMap();
                    List<Entity> entities = new ArrayList<>(innerCache.size());
                    innerCache.forEach((key, value) -> entities.add(convertToCacheEntity(key, value)));
                    return entities;
                })
                .orElseThrow(() -> new IllegalArgumentException("Replace on ExceptionFactory"));
    }

    private <K, V> Entity convertToCacheEntity(K key, V value) {
        return new Entity().key(key).value(value);
    }
}
