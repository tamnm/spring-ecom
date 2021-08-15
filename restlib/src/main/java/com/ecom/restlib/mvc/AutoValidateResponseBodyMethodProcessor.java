package com.ecom.restlib.mvc;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

@Component
public class AutoValidateResponseBodyMethodProcessor extends RequestResponseBodyMethodProcessor {

    public AutoValidateResponseBodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    protected void validateIfApplicable(WebDataBinder binder, @NotNull MethodParameter parameter) {
        binder.validate();      // always validating @RequestMapping annotated parameters ;)
    }
}
