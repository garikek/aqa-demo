package com.software.modsen.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Pet {
    private int id;
    private String name;
    private List<String> photoUrls;
    private String status;
}
