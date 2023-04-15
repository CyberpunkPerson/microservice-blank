package com.github.cyberpunkperson.blank.configuration.cache.controller;

import com.github.cyberpunkperson.blank.cache.model.Entity;
import com.github.cyberpunkperson.blank.cache.rest.CacheApiDelegate;
import com.github.cyberpunkperson.blank.configuration.cache.operation.EvictAllCacheEntitiesOperation;
import com.github.cyberpunkperson.blank.configuration.cache.operation.GetAllCacheEntitiesOperation;
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
    @Log(INFO) //todo serverTime и TraceId
    public ResponseEntity<Void> deleteAllEntities(String cacheName) {
        evictAllCacheEntitiesOperation.activate(cacheName);
        return ok().build();
    }

    @Override
    @Log(INFO)
    public ResponseEntity<List<Entity>> getAllEntities(String cacheName) {
        return ok(getAllCacheEntitiesOperation.activate(cacheName));
    }
}
