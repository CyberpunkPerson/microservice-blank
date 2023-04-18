package com.github.cyberpunkperson.template.configuration.cache;

import com.github.benmanes.caffeine.cache.Cache;

public record NamedCache(String name, Cache<?, ?> cache) {
}
