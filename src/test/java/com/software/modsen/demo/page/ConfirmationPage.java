package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmationPage {
    private final SelenideElement completeHeader = $(".complete-header");

    public ConfirmationPage assertOrderConfirmation(String expectedMessage) {
        step("Verify message about successful order", () -> {
            String actual = completeHeader.getText();
            assertThat(actual)
                    .as("Order confirmation text")
                    .isEqualTo(expectedMessage);
        });
        return this;
    }
}
