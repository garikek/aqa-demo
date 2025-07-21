package com.software.modsen.demo.tests.api;

import com.software.modsen.demo.tests.config.ApiSpecification;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {
    @BeforeAll
    static void initSpec() {
        RestAssured.requestSpecification = ApiSpecification.REQUEST_SPEC;
    }
}
