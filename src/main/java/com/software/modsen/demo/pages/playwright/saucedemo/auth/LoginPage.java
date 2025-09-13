package com.software.modsen.demo.pages.playwright.saucedemo.auth;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.inventory.InventoryPage;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class LoginPage extends SaucedemoBasePage {
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        super(page);
        page.navigate(BASE_URL);

        usernameInput = page.locator("#user-name");
        passwordInput = page.locator("#password");
        loginButton = page.locator("#login-button");
        errorMessage = page.locator("[data-test='error']");
    }

    public InventoryPage logInAs(String username, String password) {
        step("Enter credentials and click login button", () -> {
            fill(usernameInput, username);
            fill(passwordInput, password);
            click(loginButton);
        });
        return new InventoryPage(page);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
