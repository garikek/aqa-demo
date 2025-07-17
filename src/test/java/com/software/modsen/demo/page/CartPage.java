package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class CartPage {
    private final SelenideElement checkoutButton = $("#checkout");

    public CheckoutPage checkout() {
        step("Click Checkout in cart", () ->
                checkoutButton.click()
        );
        return new CheckoutPage();
    }
}
