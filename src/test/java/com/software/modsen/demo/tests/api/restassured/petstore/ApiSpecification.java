package com.software.modsen.demo.tests.api.restassured.petstore;

import com.software.modsen.demo.config.ConfigProvider;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiSpecification {
    public static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(ConfigProvider.API.getPetstore().getUrl())
                    .setBasePath(ConfigProvider.API.getPetstore().getBasePath())
                    .addFilter(new AllureRestAssured())
                    .build();
}
