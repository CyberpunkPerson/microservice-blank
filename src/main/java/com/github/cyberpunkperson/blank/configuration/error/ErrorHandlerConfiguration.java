package com.github.cyberpunkperson.blank.configuration.error;

import com.github.cyberpunkperson.blank.error.BusinessErrorCode;
import com.github.cyberpunkperson.blank.error.InvocationErrorCode;
import com.github.cyberpunkperson.blank.error.ValidationErrorCode;
import com.github.cyberpunkperson.blank.operation.OperationCode;
import com.github.cyberpunkperson.essentials.error.core.code.resolver.ErrorCodeResolvers;
import com.github.cyberpunkperson.essentials.operation.resolver.EnumOperationCodeFromNameResolver;
import com.github.cyberpunkperson.essentials.operation.resolver.OperationCodeFromNameResolver;
import com.github.cyberpunkperson.essentials.spring.web.common.util.TraceUtil;
import com.github.cyberpunkperson.essentials.web.error.response.ErrorIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ErrorHandlerConfiguration {

    @Bean
    OperationCodeFromNameResolver operationCodeFromNameResolver() {
        return new EnumOperationCodeFromNameResolver<>(OperationCode.class);
    }

    @Bean
    ErrorCodeResolvers errorCodeResolvers() {
        return ErrorCodeResolvers.builder()
                .business(BusinessErrorCode.class)
                .validation(ValidationErrorCode.class)
                .invocation(InvocationErrorCode.class)
                .build();
    }

    @Bean
    ErrorIdResolver errorIdResolver() {
        return (__) -> TraceUtil.getTraceId();
    }
}
