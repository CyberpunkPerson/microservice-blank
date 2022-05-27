package com.github.cyberpunkperson.microserviceblank.controller;

import com.github.cyberpunkperson.microserviceblank.support.OpenApiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequiredArgsConstructor
@ConditionalOnBean(OpenApiProvider.class)
class OpenApiController {

    private final OpenApiProvider openApiProvider;

    @GetMapping(value = "/docs/openapi.yaml", produces = TEXT_PLAIN_VALUE)
    public String getOpenApiContract() {
        return openApiProvider.getPreparedContract();
    }
}
