package com.github.cyberpunkperson.blank.configuration.cache.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.lang.Nullable;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@ConfigurationProperties("cache")
public record CacheConfig(
        @Nullable List<Specification> specs) {

    @ConstructorBinding
    public CacheConfig {
        specs = requireNonNullElse(specs, emptyList());
    }

    public record Specification(String name, String spec) {
    }
}
