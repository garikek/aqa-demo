package com.software.modsen.demo.config.model;

import com.software.modsen.demo.config.model.app.DemoqaAppConfig;
import com.software.modsen.demo.config.model.app.PizzeriaAppConfig;
import com.software.modsen.demo.config.model.app.SaucedemoAppConfig;
import lombok.Data;

@Data
public class AppConfig {
    private boolean headless;
    private SaucedemoAppConfig saucedemo;
    private DemoqaAppConfig demoqa;
    private PizzeriaAppConfig pizzeria;
}
