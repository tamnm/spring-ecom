package com.ecom.restlib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Configuration
@EnableOpenApi()
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

}
