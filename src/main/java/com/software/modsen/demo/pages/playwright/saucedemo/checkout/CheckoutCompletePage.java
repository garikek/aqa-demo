package com.software.modsen.demo.pages.playwright.saucedemo.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.inventory.InventoryPage;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class CheckoutCompletePage extends SaucedemoBasePage {
    private final Locator completeHeader;
    private final Locator completeText;
    private final Locator backHomeButton;

    public CheckoutCompletePage(Page page) {
        super(page);
        completeHeader = page.locator(".complete-header");
        completeText = page.locator(".complete-text");
        backHomeButton = page.locator("#back-to-products");
    }

    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    public String getCompleteText() {
        return getText(completeText);
    }

    public InventoryPage clickBackHome() {
        step("Click back home button", () -> click(backHomeButton));
        return new InventoryPage(page);
    }
}
