package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class LoginPage {
    private static final String URL = "https://www.saucedemo.com/";

    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");

    public LoginPage openPage() {
        step("Open login page", () ->
                open(URL)
        );
        return this;
    }

    public InventoryPage logInAs(String username, String password) {
        step("Enter credentials and click login button", () -> {
            usernameInput.setValue(username);
            passwordInput.setValue(password);
            loginButton.click();
        });
        return new InventoryPage();
    }
}
