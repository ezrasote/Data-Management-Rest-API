package com.rfa.employee.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket eksadAPI() { //docket menginisasilisia swagger baca yang mana aja 
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.rfa.employee")) //baca semua package yang ada rfa employee
				.paths(regex("/api.*")) //mengenerate dokumentasi mapping yang dimulai dengan /api
				.build()
				.apiInfo(metaInfo())
				.tags(
					new Tag("Division", "Division Management API"),
					new Tag("Employee", "Employee Management API"),
					new Tag("Get Data API", ""),
					new Tag("Division", ""),
					new Tag("Data Manipulation API", "")//biar dua dia dikosongin aja
				);
	}

	private ApiInfo metaInfo() {
		ApiInfo apiinfo = new ApiInfo("Employee Data Management REST API", 
				"Rest API Collection for Employee Data Management",
				"1.1.0",
				"http://www.google.com/", 
				new Contact("Achmad Fulan","http://www.fulan.com", "fulan@gmail.com"),
				"Fulan 2.0", 
				"http://www.google.com/license",
				Collections.emptyList()
				);
		return apiinfo;
				

				
				
				
	}
				
}
