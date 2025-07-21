package com.software.modsen.demo.tests.ui;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static com.software.modsen.demo.tests.config.TestConfig.UI_SAUCEDEMO_BASE_URI;

public abstract class BaseUiTest {
    @BeforeAll
    static void setupAllureListener() {
        addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(false)
        );
    }

    @BeforeEach
    public void openPage() {
        open(UI_SAUCEDEMO_BASE_URI);
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }
}
