package com.github.cyberpunkperson.template.support;

import jakarta.servlet.ServletContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@ConditionalOnProperty(value = "server.openapi.enabled", havingValue = "true")
public class OpenApiProvider {

    @Getter
    private final String preparedContract;

    public OpenApiProvider(@Value("classpath:openapi/template-contract.yaml") Resource contractResource,
                           ServletContext servletContext) throws IOException {
        String contractString = new String(contractResource.getInputStream().readAllBytes(), UTF_8);

        int openapiIndex = contractString.indexOf("openapi:");
        int afterOpenapiNextLineIndex = contractString.indexOf('\n', openapiIndex);

        preparedContract = contractString.substring(0, afterOpenapiNextLineIndex + 1) +
                           "\nservers:\n  - url: " +
                           servletContext.getContextPath() +
                           '\n' +
                           contractString.substring(afterOpenapiNextLineIndex + 1);
    }
}
