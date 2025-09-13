package com.software.modsen.demo.tests.ui.playwright;

import com.microsoft.playwright.*;
import com.software.modsen.demo.config.ConfigProvider;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;

public abstract class BasePlaywrightUiTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(ConfigProvider.APP.isHeadless())
                );
    }

    @BeforeEach
    void createContext() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );
        page = context.newPage();
    }

    @AfterEach
    void tearDownContext(TestInfo testInfo) throws IOException {
        // create trace archive for each test method
        String method = testInfo.getTestMethod()
                .map(Method::getName)
                .orElse("unknown_test");
        Path tracesDir = Paths.get("traces");
        Files.createDirectories(tracesDir);

        Path traceFile = tracesDir.resolve(method + ".zip");

        context.tracing().stop(new Tracing.StopOptions()
                .setPath(traceFile)
        );

        // attach it to allure report
        byte[] content = Files.readAllBytes(traceFile);
        Allure.addAttachment(
                "Trace: " + method,
                "application/zip",
                new ByteArrayInputStream(content),
                ".zip"
        );

        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        playwright.close();
    }
}
