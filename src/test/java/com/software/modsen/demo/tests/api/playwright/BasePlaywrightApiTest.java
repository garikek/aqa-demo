package com.software.modsen.demo.tests.api.playwright;

import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePlaywrightApiTest {
    protected static Playwright playwright;

    @BeforeAll
    void initPlaywright() {
        playwright = Playwright.create();
    }

    @AfterAll
    void closePlaywright() {
        playwright.close();
    }
}
