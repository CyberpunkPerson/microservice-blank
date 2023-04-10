package com.github.cyberpunkperson.blank.support.cache.controller;

import com.github.cyberpunkperson.blank.cache.model.Entity;
import com.github.cyberpunkperson.blank.cache.rest.CacheApiDelegate;
import com.github.cyberpunkperson.blank.support.cache.operation.EvictAllCacheEntitiesOperation;
import com.github.cyberpunkperson.blank.support.cache.operation.GetAllCacheEntitiesOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.tinkoff.eclair.annotation.Log;

import java.util.List;

import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.http.ResponseEntity.ok;

@Component //todo make conditional
@RequiredArgsConstructor
public class CacheController implements CacheApiDelegate {

    private final EvictAllCacheEntitiesOperation evictAllCacheEntitiesOperation;
    private final GetAllCacheEntitiesOperation getAllCacheEntitiesOperation;

    @Override
    @Log(INFO)
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
