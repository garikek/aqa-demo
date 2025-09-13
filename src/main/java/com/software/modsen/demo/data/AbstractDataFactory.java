package com.software.modsen.demo.data;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDataFactory {
    protected static final Faker faker = new Faker();

    public static <T> List<T> randomList(Supplier<T> supplier, int count) {
        return Stream.generate(supplier)
                .limit(count)
                .toList();
    }
}
