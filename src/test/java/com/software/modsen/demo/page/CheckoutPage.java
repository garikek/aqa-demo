package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class CheckoutPage {
    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");

    public OverviewPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        step(String.format("Fill out user information: %s, %s, %s", firstName, lastName, postalCode), () -> {
                    firstNameInput.setValue(firstName);
                    lastNameInput.setValue(lastName);
                    postalCodeInput.setValue(postalCode);
                    continueButton.click();
                }
        );
        return new OverviewPage();
    }
}
