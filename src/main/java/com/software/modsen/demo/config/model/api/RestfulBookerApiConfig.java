package com.software.modsen.demo.config.model.api;

import lombok.Data;

@Data
public class RestfulBookerApiConfig {
    private String url;
    private String baseApiPath;
    private String defaultUsername;
    private String defaultPassword;
}
