package com.github.cyberpunkperson.blank.error;

import com.github.cyberpunkperson.essentials.error.core.EnumErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvocationErrorCode implements EnumErrorCode.Invocation {

    ;

    private final String message;

}
