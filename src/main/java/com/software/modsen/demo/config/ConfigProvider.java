package com.software.modsen.demo.config;

import com.software.modsen.demo.config.model.ApiConfig;
import com.software.modsen.demo.config.model.AppConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigProvider {
    private static final Config CONFIG = initConfig();

    public static final AppConfig APP = ConfigBeanFactory.create(CONFIG.getConfig("app"), AppConfig.class);

    public static final ApiConfig API = ConfigBeanFactory.create(CONFIG.getConfig("api"), ApiConfig.class);

    private static Config initConfig() {
        String profile = System.getProperty("testProfile");

        File external = Paths.get("resources", "config", "application.conf").toFile();
        Config fileConfig = ConfigFactory.parseFile(external);

        Config fallback = (profile != null && !profile.isBlank())
                ? ConfigFactory.load(profile)
                : ConfigFactory.load();

        return fileConfig.withFallback(fallback).resolve();
    }
}
