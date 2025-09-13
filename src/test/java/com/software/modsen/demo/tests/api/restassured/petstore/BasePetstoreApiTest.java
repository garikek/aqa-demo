package com.software.modsen.demo.tests.api.restassured.petstore;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BasePetstoreApiTest {
    @BeforeAll
    static void initSpec() {
        RestAssured.requestSpecification = ApiSpecification.REQUEST_SPEC;
    }
}
