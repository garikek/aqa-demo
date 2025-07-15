package com.software.modsen.demo.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.step;

@Feature("E2E Purchase Flow")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SauceDemoUiTest {

    @BeforeAll
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.timeout = 5000;

        step("Configure Selenide and start browser", () -> addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(true)
        ));
    }

    @AfterAll
    void tearDown() {
        step("Close browser", Selenide::closeWebDriver);
    }

    /**
     * Test Case: Standard user E2E purchase flow
     * Goal:
     *      Verify that a standard_user can log in, add an item,
     *      proceed to checkout, fill shipping info, and see order confirmation
     */
    @Test
    @Story("Standard user completes an order")
    @Description("Verify that a standard_user can log in, add an item, checkout, fill info and see confirmation")
    @Severity(SeverityLevel.CRITICAL)
    void standardUserCanCheckout() {
        login();
        addFirstItemToCart();
        proceedToCheckout();
        fillShippingInfo();
        finishOrderAndVerify();
    }

    /**
     * Login as standard_user.
     * Verifies inventory list is visible after successful login.
     */
    void login() {
        step("Login as standard_user", () -> {
            open("https://www.saucedemo.com/");
            $("#user-name").setValue("standard_user");
            $("#password").setValue("secret_sauce");
            $("#login-button").click();
            $(".inventory_list").should(exist);
        });
    }

    /**
     * Adds the first displayed inventory item to the cart.
     * Asserts that the cart badge updates to show 1 item.
     */
    void addFirstItemToCart() {
        step("Add the first item to the cart", () -> {
            $$(".inventory_item").first()
                    .$(".btn_inventory")
                    .click();

            $(".shopping_cart_badge")
                    .shouldBe(visible)
                    .shouldHave(text("1"));
        });
    }

    /**
     * Opens the shopping cart and clicks the Checkout button.
     * Asserts the checkout information form is displayed.
     */
    void proceedToCheckout() {
        step("Open cart and click Checkout", () -> {
            $(".shopping_cart_link").click();
            $("#checkout").click();
            $("#first-name").should(exist);
        });
    }

    /**
     * Fills in the shipping information form and continues.
     * Asserts the order overview summary is visible.
     */
    void fillShippingInfo() {
        step("Fill shipping info: Name='Name', Surname='Surname', Postal='123456'", () -> {
            $("#first-name").setValue("Name");
            $("#last-name").setValue("Surname");
            $("#postal-code").setValue("123456");
            $("#continue").click();
            $(".summary_info").should(exist);
        });
    }

    /**
     * Completes the order and verifies the success message appears.
     */
    void finishOrderAndVerify() {
        step("Finish the order and verify confirmation", () -> {
            $("#finish").click();
            $(".complete-header")
                    .shouldHave(text("Thank you for your order!"));
        });
    }
}
