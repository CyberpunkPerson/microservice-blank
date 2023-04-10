package com.github.cyberpunkperson.blank.support.cache.configuration;


//@ConfigSource("cache")
public record CacheConfig(boolean isEndpointEnabled,
                          Specification example) {
    public record Specification(String spec) {
    }
}
