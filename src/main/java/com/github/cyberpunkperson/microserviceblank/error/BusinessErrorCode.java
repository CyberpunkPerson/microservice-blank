package com.github.cyberpunkperson.microserviceblank.error;

import com.github.cyberpunkperson.errorchef.core.error.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode implements EnumErrorCode.Business {


    ;

    private final String message;

}
