package com.software.modsen.demo.tests.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.software.modsen.demo.tests.config.TestConfig.API_PETSTORE_BASE_PATH;
import static com.software.modsen.demo.tests.config.TestConfig.API_PETSTORE_BASE_URI;

public final class ApiSpecification {
    public static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(API_PETSTORE_BASE_URI)
                    .setBasePath(API_PETSTORE_BASE_PATH)
                    .addFilter(new AllureRestAssured())
                    .build();
}
