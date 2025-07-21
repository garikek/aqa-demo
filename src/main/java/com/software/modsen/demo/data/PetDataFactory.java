package com.software.modsen.demo.data;

import com.github.javafaker.Faker;
import com.software.modsen.demo.model.Pet;

import java.util.List;

public class PetDataFactory {
    private static final Faker FAKER = new Faker();
    private static final List<String> STATUSES = List.of("available", "pending", "sold");

    public static Pet uniquePet() {
        int id = FAKER.number().numberBetween(100000, 1000000);
        String name = FAKER.cat().name() + "_" + id;
        List<String> photoUrls = List.of(FAKER.internet().avatar());
        String status = FAKER.options().option(STATUSES.toArray(new String[0]));

        return new Pet(id, name, photoUrls, status);
    }
}
