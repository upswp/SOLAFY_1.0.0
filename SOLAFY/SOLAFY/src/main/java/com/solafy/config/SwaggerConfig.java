package com.solafy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:9999/solafy/swagger-ui.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("solafy")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.solafy.controller"))
				.paths(PathSelectors.ant("/**"))
				.build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SOLAFY API")
				.description("SOLAFY API Reference for Developers")
				//TODO : 호스팅 했을 때, 주소 넣기
				.termsOfServiceUrl("https://edu.ssafy.com") 
				.license("SOLAFY License")
				.licenseUrl("solafy@gmail.com").version("1.0").build();
	}
}
