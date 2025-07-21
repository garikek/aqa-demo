package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Getter
public class LoginPage {
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");

    public InventoryPage logInAs(String username, String password) {
        step("Enter credentials and click login button", () -> {
            usernameInput.setValue(username);
            passwordInput.setValue(password);
            loginButton.click();
        });
        return new InventoryPage();
    }
}
