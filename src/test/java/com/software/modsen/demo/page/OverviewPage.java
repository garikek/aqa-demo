package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class OverviewPage {
    private final SelenideElement finishButton = $("#finish");

    public ConfirmationPage finishCheckout() {
        step("Finish checkout", () ->
                finishButton.click()
        );
        return new ConfirmationPage();
    }
}
