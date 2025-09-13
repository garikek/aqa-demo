package com.software.modsen.demo.pages.playwright.saucedemo.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class CheckoutPage extends SaucedemoBasePage {
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalCodeInput;
    private final Locator continueButton;

    public CheckoutPage(Page page) {
        super(page);
        firstNameInput = page.locator("#first-name");
        lastNameInput = page.locator("#last-name");
        postalCodeInput = page.locator("#postal-code");
        continueButton = page.locator("#continue");
    }

    public CheckoutPage fillCustomerInfo(String firstName, String lastName, String postalCode) {
        step(String.format("Fill out user information: %s, %s, %s", firstName, lastName, postalCode), () -> {
            fill(firstNameInput, firstName);
            fill(lastNameInput, lastName);
            fill(postalCodeInput, postalCode);
        });
        return this;
    }

    public CheckoutOverviewPage clickContinue() {
        step("Click continue button", () -> click(continueButton));
        return new CheckoutOverviewPage(page);
    }
}
