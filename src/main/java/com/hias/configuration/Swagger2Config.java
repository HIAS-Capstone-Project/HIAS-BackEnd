package com.hias.configuration;

import com.hias.constant.DataTypeConstant;
import com.hias.constant.SecurityConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    private static final String API_SCAN_PACKAGE = "com.hias";
    private static final String API_PATH_PATTERN = ".*";
    private static final String SWAGGER_TITLE = "HIAS API Documents";
    private static final String SWAGGER_DESCRIPTION = "Documents with Swagger 2";
    private static final String SWAGGER_LICENSE = "hias";
    private static final String SWAGGER_VERSION = "2.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Collections.singletonList(
                        new ParameterBuilder()
                                .name(SecurityConstant.AUTHORIZATION)
                                .modelRef(new ModelRef(DataTypeConstant.STRING))
                                .parameterType(SecurityConstant.HEADER)
                                .required(true)
                                .hidden(true)
                                .defaultValue(SecurityConstant.BEARER_TOKEN)
                                .build()
                )).select()
                .apis(RequestHandlerSelectors
                        .basePackage(API_SCAN_PACKAGE))
                .paths(PathSelectors.regex(API_PATH_PATTERN))
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title(SWAGGER_TITLE)
                .description(SWAGGER_DESCRIPTION)
                .license(SWAGGER_LICENSE)
                .version(SWAGGER_VERSION).build();
    }
}
