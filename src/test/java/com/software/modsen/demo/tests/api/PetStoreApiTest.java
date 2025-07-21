package com.software.modsen.demo.tests.api;

import com.software.modsen.demo.model.Pet;
import com.software.modsen.demo.service.PetService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.software.modsen.demo.data.PetDataFactory.uniquePet;
import static io.qameta.allure.Allure.step;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@Epic("PetStore API")
@Feature("Pet Management")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PetStore API Endpoints Tests")
public class PetStoreApiTest extends BaseApiTest {
    private static final PetService service = new PetService();
    private static Pet testPet;

    @BeforeAll
    static void initPet() {
        testPet = uniquePet();
    }

    @Test
    @Order(1)
    @DisplayName("PET-001: Cleanup leftover pet")
    @Story("Delete existing pet")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
        Test Case ID: PET-001
        Title: Cleanup leftover pet
        Preconditions:
          - Base URI is set to https://petstore.swagger.io/v2
          - Pet with this ID may or may not exist
        
        Steps:
          1. DELETE /pet/{uniqueId}
        
        Expected:
          - HTTP 200 or 404
        """)
    void cleanupLeftoverPet() {
        step("Delete pet if exists (id=" + testPet.getId() + ")", () -> {
            Response response = service.deleteIfExists(testPet.getId());
            response.then()
                    .statusCode(anyOf(is(200), is(404)));
        });
    }

    @Test
    @Order(2)
    @DisplayName("PET-002: Create new pet with unique ID")
    @Story("Create new pet")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
        Test Case ID: PET-002
        Title: Create new pet with unique ID
        Preconditions:
          - Base URI is set to https://petstore.swagger.io/v2
          - No pet exists with this unique ID
        
        Steps:
          1. Build JSON payload with id, name, photoUrls, status
          2. POST /pet with the payload
        
        Expected:
          - HTTP 200
          - Response body fields `id`, `name`, `status` match the payload
        """)
    void createNewPet() {
        step("Create pet", () -> {
            Response response = service.createPet(testPet);
            response.then()
                    .statusCode(200)
                    .body("id",   is(testPet.getId()))
                    .body("name", is(testPet.getName()))
                    .body("status", is(testPet.getStatus()));
        });
    }

    @Test
    @Order(3)
    @DisplayName("PET-003: Retrieve and verify created pet")
    @Story("Get pet by ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
        Test Case ID: PET-003
        Title: Retrieve and verify created pet
        Preconditions:
          - Pet with this unique ID was just created
        
        Steps:
          1. Poll GET /pet/{uniqueId} every 0.5s for up to 15s
          2. Stop polling once HTTP 200 is returned
        
        Expected:
          - HTTP 200
          - Response body fields `id`, `name`, `status` match the original payload
        """)
    void retrieveCreatedPet() {
        step("Await pet creation and verify response", () -> await()
                .pollInterval(Duration.ofMillis(500))
                .atMost(Duration.ofSeconds(15))
                .untilAsserted(() -> {
                    Response response = service.getPetById(testPet.getId());
                    response.then()
                            .statusCode(200)
                            .body("id",   is(testPet.getId()))
                            .body("name", is(testPet.getName()))
                            .body("status", is(testPet.getStatus()));
                }));
    }
}
