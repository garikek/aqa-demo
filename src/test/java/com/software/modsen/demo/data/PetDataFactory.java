package com.software.modsen.demo.data;

import com.software.modsen.demo.model.Pet;

import java.util.Collections;
import java.util.Random;

public class PetDataFactory {
    public static Pet uniquePet() {
        int id = Math.abs(new Random().nextInt());
        String name = "TestPet_" + id;
        return new Pet(id, name, Collections.singletonList("http://example.com/photo.jpg"), "available");
    }
}
