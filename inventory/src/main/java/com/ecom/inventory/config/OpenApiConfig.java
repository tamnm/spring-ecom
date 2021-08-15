package com.ecom.inventory.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class OpenApiConfig {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(regex("/api.+"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Ecom-Inventory APIs",
                           "Sample APIs inventory microservice in Ecom",
                           "v1",
                           Strings.EMPTY,
                           new Contact("TamNM", Strings.EMPTY, "nguyen@manhtam.com"),
                           "Apache 2.0",
                           Strings.EMPTY,
                           Collections.emptyList());
    }
}
