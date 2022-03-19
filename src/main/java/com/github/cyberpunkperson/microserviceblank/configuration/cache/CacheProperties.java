package com.github.cyberpunkperson.microserviceblank.configuration.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "cache")
record CacheProperties(List<CacheSpecification> specifications) {

    record CacheSpecification(String name, String spec) {
    }
}
