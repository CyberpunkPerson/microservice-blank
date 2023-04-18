package com.github.cyberpunkperson.template.error;

import com.github.cyberpunkperson.essentials.error.core.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements EnumErrorCode.Business {

    CACHE_NOT_FOUND("Cache not found")
    ;

    private final String message;

}
