package com.github.cyberpunkperson.microserviceblank.configuration.error;

import com.github.cyberpunkperson.errorchef.web.error.response.ErrorIdResolver;
import com.github.cyberpunkperson.errorchef.web.error.response.ErrorResponseData;
import com.github.cyberpunkperson.errorchef.web.error.response.ErrorResponseMapper;
import com.github.cyberpunkperson.microserviceblank.model.ErrorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
abstract class DefaultErrorResponseMapper implements ErrorResponseMapper<ErrorResponse> {

    @Autowired
    protected ErrorIdResolver errorIdResolver;

    @Mapping(source = ".", target = "errorId", qualifiedByName = "mapErrorId")
    public abstract ErrorResponse mapResponse(ErrorResponseData errorResponseData);

    @Named("mapErrorId")
    protected String mapErrorId(ErrorResponseData errorResponseData) {
        return errorIdResolver.resolve(errorResponseData);
    }
}
