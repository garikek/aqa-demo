package com.software.modsen.demo.service;

import com.software.modsen.demo.client.PetClient;
import com.software.modsen.demo.model.Pet;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class PetService {
    private final PetClient client = new PetClient();

    public void deleteIfExists(int petId) {
        client.deletePet(petId)
                .then()
                .statusCode(anyOf(is(200), is(404)));
    }

    public void createPet(Pet pet) {
        client.createPet(pet)
                .then()
                .statusCode(200)
                .body("id", is(pet.getId()))
                .body("name", is(pet.getName()))
                .body("status", is(pet.getStatus()));
    }

    public Response getPetById(int petId) {
        return client.getPetById(petId);
    }
}
