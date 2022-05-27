package com.github.cyberpunkperson.microserviceblank.support.constants;

import com.github.cyberpunkperson.errorchef.core.operation.resolver.MdcOperationNameResolver;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MdcKey {

    public static final String OPERATION_NAME = MdcOperationNameResolver.OPERATION_NAME_MDC_KEY;
    public static final String TRACE_ID = "x-b3-traceid";

}
