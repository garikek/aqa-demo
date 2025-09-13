package com.software.modsen.demo.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Attachment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

//    public static String toJson(Object object) {
//        try {
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Serialization to JSON failed", e);
//        }
//    }

    @Attachment(value = "{name}", type = "application/json")
    public static String toJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization to JSON failed", e);
        }
    }

    public static <T> T fromJson(APIResponse response, Class<T> clazz) {
        try {
            return mapper.readValue(response.body(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization of response into " + clazz + " failed", e);
        }
    }
}
