package com.software.modsen.demo.service;

import com.software.modsen.demo.client.PetClient;
import com.software.modsen.demo.model.Pet;
import io.restassured.response.Response;

public class PetService {
    private final PetClient client = new PetClient();

    public Response deleteIfExists(int petId) {
        return client.deletePet(petId);
    }

    public Response createPet(Pet pet) {
        return client.createPet(pet);
    }

    public Response getPetById(int petId) {
        return client.getPetById(petId);
    }
}
