package com.github.cyberpunkperson.microserviceblank.configuration.error;

import com.github.cyberpunkperson.errorchef.core.error.code.resolver.ErrorCodeResolvers;
import com.github.cyberpunkperson.errorchef.core.operation.resolver.EnumOperationCodeFromNameResolver;
import com.github.cyberpunkperson.errorchef.core.operation.resolver.OperationCodeFromNameResolver;
import com.github.cyberpunkperson.errorchef.web.error.response.ErrorIdResolver;
import com.github.cyberpunkperson.errorchef.web.error.response.ErrorResponseMapper;
import com.github.cyberpunkperson.microserviceblank.error.BusinessErrorCode;
import com.github.cyberpunkperson.microserviceblank.error.InvocationErrorCode;
import com.github.cyberpunkperson.microserviceblank.error.ValidationErrorCode;
import com.github.cyberpunkperson.microserviceblank.model.ErrorResponse;
import com.github.cyberpunkperson.microserviceblank.operation.OperationCode;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.cyberpunkperson.microserviceblank.support.constants.MdcKey.TRACE_ID;

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
    ErrorResponseMapper<ErrorResponse> errorResponseMapper(DefaultErrorResponseMapper defaultErrorResponseMapper) {
        return defaultErrorResponseMapper;
    }

    @Bean
    ErrorIdResolver errorIdResolver() {
        return (__) -> MDC.get(TRACE_ID);
    }
}
