package com.ecom.restlib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AutoValidateResolverConfig {

    RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    public AutoValidateResolverConfig(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor,
                                      RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @PostConstruct
    public void init() {
        //get the resolver list and replace the default one by the Auto validate one.
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();

        if (argumentResolvers == null) return;

        final List<HandlerMethodArgumentResolver> mangledResolvers
                = argumentResolvers.stream()
                                   .map(resolver -> mapResolver(resolver, requestResponseBodyMethodProcessor))
                                   .collect(Collectors.toList());

        //set back to adapter.
        requestMappingHandlerAdapter.setArgumentResolvers(mangledResolvers);
    }

    private HandlerMethodArgumentResolver mapResolver(HandlerMethodArgumentResolver resolver,
                                                      RequestResponseBodyMethodProcessor processor) {
        //replace default RequestResponseBodyMethodProcessor by the custom one.
        return resolver.getClass().equals(RequestResponseBodyMethodProcessor.class)
               ? processor
               : resolver;
    }

}