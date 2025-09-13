package com.software.modsen.demo.config.model;

import com.software.modsen.demo.config.model.api.PetstoreApiConfig;
import com.software.modsen.demo.config.model.api.RestfulBookerApiConfig;
import lombok.Data;

@Data
public class ApiConfig {
    private PetstoreApiConfig petstore;
    private RestfulBookerApiConfig restfulBooker;
}
