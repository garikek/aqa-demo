package com.software.modsen.demo.tests.api.playwright.booker;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.software.modsen.demo.client.BookingClient;
import com.software.modsen.demo.config.ConfigProvider;
import com.software.modsen.demo.tests.api.playwright.BasePlaywrightApiTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BookingClientBaseTest extends BasePlaywrightApiTest {
    protected APIRequestContext apiRequestContext;
    protected BookingClient client;

    @BeforeAll
    void setUpClient() {
        String baseUrl = ConfigProvider.API.getRestfulBooker().getUrl();

        apiRequestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(Map.of(
                                "Content-Type", "application/json",
                                "Accept", "application/json"
                        ))
        );

        client = new BookingClient(apiRequestContext);
    }

    @AfterAll
    void tearDownClient() {
        apiRequestContext.dispose();
    }
}
