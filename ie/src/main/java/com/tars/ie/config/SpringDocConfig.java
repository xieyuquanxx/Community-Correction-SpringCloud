package com.tars.ie.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Doc 配置
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI restfulOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("调查评估模块 API")
                                .description(
                                        "调查评估信息，调查评估意见，调查评估流程 APi")
                                .version("v0.0.1")
                                .license(new License().name(
                                                              "Apache 2.0")
                                                      .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Wiki Documentation")
                        .url("https://springdoc.org/v2"));
    }
}
