package com.github.cyberpunkperson.template.error;

import com.github.cyberpunkperson.essentials.error.core.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode implements EnumErrorCode.Validation {


    ;

    private final String message;

}
