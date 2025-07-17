package com.software.modsen.demo.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetClient {
    private static final String BASE = "/pet";

    public Response createPet(Object payload) {
        return given()
                .contentType("application/json")
                .body(payload)
                .post(BASE);
    }

    public Response getPetById(int id) {
        return given()
                .pathParam("petId", id)
                .get(BASE + "/{petId}");
    }

    public Response deletePet(int id) {
        return given()
                .pathParam("petId", id)
                .delete(BASE + "/{petId}");
    }
}
