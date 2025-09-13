package com.software.modsen.demo.pages.playwright.saucedemo.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.checkout.CheckoutPage;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class CartPage extends SaucedemoBasePage {
    private final Locator cartItems;
    private final Locator checkoutButton;

    public CartPage(Page page) {
        super(page);
        cartItems = page.locator(".cart_item");
        checkoutButton = page.locator("#checkout");
    }

    public int getNumberOfItemsInCart() {
        return getCount(cartItems);
    }

    public CheckoutPage proceedToCheckout() {
        step("Click checkout button", () -> click(checkoutButton));
        return new CheckoutPage(page);
    }
}
