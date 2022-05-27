package com.github.cyberpunkperson.microserviceblank.configuration.web.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum WebFilterOrder {
    FIRST(Integer.MIN_VALUE),

    CORS,
    REQUEST_PARSE,
    MDC_POPULATION,

    LAST(Integer.MAX_VALUE);

    private static final int INTERVAL = 100;
    private final int order;

    WebFilterOrder() {
        this.order = this.ordinal() * INTERVAL;
    }
}
