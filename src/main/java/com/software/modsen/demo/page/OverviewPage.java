package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Getter
public class OverviewPage {
    private final SelenideElement finishButton = $("#finish");

    public ConfirmationPage finishCheckout() {
        step("Finish checkout", () ->
                finishButton.click()
        );
        return new ConfirmationPage();
    }
}
