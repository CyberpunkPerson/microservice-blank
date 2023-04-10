package com.github.cyberpunkperson.blank.configuration.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.cyberpunkperson.blank.support.constants.MdcKey.TRACE_ID;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class MdcRequestFilter extends OncePerRequestFilter {

    private static final String SERVER_TIME = "x-server-time";

    private final RequestMappingHandlerMapping requestHandlerMapping;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader(TRACE_ID, populateTraceId(request));
        response.addHeader(SERVER_TIME, OffsetDateTime.now().truncatedTo(MILLIS).toString());

        var operationName = getOperationName(request);
        if (operationName.isEmpty()) {
            response.setStatus(SC_NOT_FOUND);
            return;
        }

        try {
//            todo replace
            MDC.put("operationName", operationName.get());
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e); //todo rid of
        } finally {
            MDC.clear();
        }
    }

    private static String populateTraceId(HttpServletRequest request) {
        String traceId = getTraceId(request);
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }

    private static String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TRACE_ID);
        return traceId == null || traceId.isBlank() ? nextId() : traceId;
    }

    private static String nextId() {
        var id = ThreadLocalRandom.current().nextLong();
        return toLowerHex(id);
    }

    private static String toLowerHex(long v) {
        char[] data = new char[16];
        writeHexLong(data, 0, v);
        return new String(data, 0, 16);
    }

    /**
     * Inspired by {@code okio.Buffer.writeLong}
     */
    private static void writeHexLong(char[] data, int pos, long v) {
        writeHexByte(data, pos + 0, (byte) ((v >>> 56L) & 0xff));
        writeHexByte(data, pos + 2, (byte) ((v >>> 48L) & 0xff));
        writeHexByte(data, pos + 4, (byte) ((v >>> 40L) & 0xff));
        writeHexByte(data, pos + 6, (byte) ((v >>> 32L) & 0xff));
        writeHexByte(data, pos + 8, (byte) ((v >>> 24L) & 0xff));
        writeHexByte(data, pos + 10, (byte) ((v >>> 16L) & 0xff));
        writeHexByte(data, pos + 12, (byte) ((v >>> 8L) & 0xff));
        writeHexByte(data, pos + 14, (byte) (v & 0xff));
    }

    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static void writeHexByte(char[] data, int pos, byte b) {
        data[pos + 0] = HEX_DIGITS[(b >> 4) & 0xf];
        data[pos + 1] = HEX_DIGITS[b & 0xf];
    }

    private Optional<String> getOperationName(HttpServletRequest request) {
        try {
            return ofNullable(requestHandlerMapping.getHandler(request))
                    .map(mapping -> ((HandlerMethod) mapping.getHandler()).getMethod())
                    .map(Method::getName);
        } catch (Exception e) {
            return empty();
        }
    }
}
