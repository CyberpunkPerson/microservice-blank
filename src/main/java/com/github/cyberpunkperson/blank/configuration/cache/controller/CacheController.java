package com.github.cyberpunkperson.template.configuration.cache.controller;

import com.github.cyberpunkperson.template.cache.model.Entity;
import com.github.cyberpunkperson.template.cache.rest.CacheApiDelegate;
import com.github.cyberpunkperson.template.configuration.cache.operation.EvictAllCacheEntitiesOperation;
import com.github.cyberpunkperson.template.configuration.cache.operation.GetAllCacheEntitiesOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.eclair.annotation.Log;

import java.util.List;

import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
public class CacheController implements CacheApiDelegate {

    private final EvictAllCacheEntitiesOperation evictAllCacheEntitiesOperation;
    private final GetAllCacheEntitiesOperation getAllCacheEntitiesOperation;

    @Override
    @Log(INFO)
    public ResponseEntity<Void> evictAllCacheEntities(String cacheName) {
        evictAllCacheEntitiesOperation.activate(cacheName);
        return ok().build();
    }

    @Override
    @Log(INFO)
    public ResponseEntity<List<Entity>> getAllCacheEntities(String cacheName) {
        return ok(getAllCacheEntitiesOperation.activate(cacheName));
    }
}
