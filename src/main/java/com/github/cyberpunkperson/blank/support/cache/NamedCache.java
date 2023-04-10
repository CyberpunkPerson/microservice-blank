package com.github.cyberpunkperson.blank.support.cache;

import com.github.benmanes.caffeine.cache.Cache;

public record NamedCache(String name, Cache<?, ?> cache) {
}
