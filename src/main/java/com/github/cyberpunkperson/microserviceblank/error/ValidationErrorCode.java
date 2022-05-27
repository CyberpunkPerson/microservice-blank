package com.github.cyberpunkperson.microserviceblank.error;

import com.github.cyberpunkperson.errorchef.core.error.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationErrorCode implements EnumErrorCode.Validation {

    CACHE_NOT_FOUND("Cache not found"),

    ;

    private final String message;

}
