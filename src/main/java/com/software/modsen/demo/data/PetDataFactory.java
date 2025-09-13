package com.software.modsen.demo.data;

import com.software.modsen.demo.dto.request.Pet;

import java.util.List;

public class PetDataFactory extends AbstractDataFactory {
    private static final List<String> STATUSES = List.of("available", "pending", "sold");

    public static Pet uniquePet() {
        int id = faker.number().numberBetween(100000, 1000000);
        String name = faker.cat().name() + "_" + id;
        List<String> photoUrls = List.of(faker.internet().avatar());
        String status = faker.options().option(STATUSES.toArray(new String[0]));

        return new Pet(id, name, photoUrls, status);
    }

    public static List<Pet> randomPets(int count) {
        return randomList(PetDataFactory::uniquePet, count);
    }
}
