package com.github.cyberpunkperson.microserviceblank.configuration.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ServletRequestPathFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.Filter;

import static com.github.cyberpunkperson.microserviceblank.configuration.web.filter.WebFilterOrder.*;


@Configuration(proxyBeanMethods = false)
class WebFilterConfiguration {

    @Bean
    FilterRegistrationBean<ServletRequestPathFilter> servletRequestPathFilter() {
        return buildFilter(REQUEST_PARSE, new ServletRequestPathFilter());
    }

    @Bean
    FilterRegistrationBean<MdcRequestFilter> mdcRequestFilter(RequestMappingHandlerMapping requestHandlerMapping) {
        return buildFilter(MDC_POPULATION, new MdcRequestFilter(requestHandlerMapping));
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return buildFilter(CORS, new CorsFilter((__) -> corsConfiguration));
    }

    private static <T extends Filter> FilterRegistrationBean<T> buildFilter(WebFilterOrder filterOrder, T filter) {
        var filterRegistration = new FilterRegistrationBean<>(filter);
        filterRegistration.setOrder(filterOrder.getOrder());
        return filterRegistration;
    }
}
