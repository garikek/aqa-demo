package com.software.modsen.demo.api;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Random;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;

@Epic("PetStore API")
@Feature("Pet Management")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetStoreApiTest {
    private static final int uniqueId;
    private static final String uniqueName;
    private static final String status;

    static {
        uniqueId   = Math.abs(new Random().nextInt());
        uniqueName = "TestPet_" + uniqueId;
        status = "available";
    }

    @BeforeAll
    static void setupBaseUri() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    /**
     * Test Case: Cleanup leftover pet
     * Goal:
     *      Ensure a pet with 'uniqueId' doesn't exist
     * Test steps:
     *      1. DELETE /pet/{uniqueId}
     *      2. Accept either 200 or 404 as valid responses
     */
    @Test
    @Order(1)
    @Story("Delete existing pet")
    @Severity(SeverityLevel.NORMAL)
    @Description("Deletes any pet with our generated ID; accepts 200 or 404 as success.")
    void cleanupLeftoverPet() {
        deletePetIfExists();
    }

    /**
     * Test Case: Add new pet
     * Goal:
     *      Create a fresh pet with a 'uniqueId'
     *      and verify the server response
     * Test steps:
     *      1. Create JSON payload with id, name, photoUrls, status
     *      2. POST /pet with that payload
     *      3. Assert status code 200
     *      4. Assert response body fields match the sent data: id, name, status
     */
    @Test
    @Order(2)
    @Story("Create new pet")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
        1. Build JSON payload
        2. POST /pet
        3. Assert 200 and that response body fields match the payload
        """)
    void testAddNewPet() {
        String petPayload = buildPetPayload();
        createPet(petPayload);
    }

    /**
     * Test Case: Retrieve and verify created pet
     * Goal:
     *      Ensure the pet created in the previous step can be fetched
     *      and its data matches exactly what was sent
     * Test steps:
     *      1. Poll (every 0.5 seconds for 15 seconds) GET /pet/{uniqueId} until status 200
     *      2. Assert response body fields match the created pet: id, name, status
     */
    @Test
    @Order(3)
    @Story("Get pet by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Polls GET /pet/{id} until 200, then asserts all fields match the original payload.")
    void testGetPetById() {
        getPetById();
    }

    void deletePetIfExists() {
        step("DELETE /pet/{id} (if exists)", () -> {
            given()
                    .pathParam("petId", uniqueId)
                    .when()
                    .delete("/pet/{petId}")
                    .then()
                    .statusCode(anyOf(is(200), is(404)));
        });
    }

    void createPet(String petPayload) {
        step("POST /pet with payload", () -> {
            given()
                    .contentType("application/json")
                    .body(petPayload)
                    .when()
                    .post("/pet")
                    .then()
                    .statusCode(200)
                    .body("id",   is(uniqueId))
                    .body("name", is(uniqueName))
                    .body("status", is(status));
        });
    }

    void getPetById() {
        step("Poll GET /pet/{id} until 200", () -> await()
                .pollInterval(Duration.ofMillis(500))
                .atMost(Duration.ofSeconds(15))
                .untilAsserted(() ->
                        given()
                                .pathParam("petId", uniqueId)
                                .when()
                                .get("/pet/{petId}")
                                .then()
                                .statusCode(200)
                                .body("id",   is(uniqueId))
                                .body("name", is(uniqueName))
                                .body("status", is(status))));
    }

    private static String buildPetPayload() {
        return "{"
                + "\"id\": " + uniqueId + ","
                + "\"name\": \"" + uniqueName + "\","
                + "\"photoUrls\": [\"http://example.com/photo.jpg\"],"
                + "\"status\": \"" + status + "\""
                + "}";
    }
}
