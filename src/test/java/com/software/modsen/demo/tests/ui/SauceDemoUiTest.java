package com.software.modsen.demo.tests.ui;

import com.software.modsen.demo.page.LoginPage;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("E2E Purchase Flow")
@DisplayName("SauceDemo UI Tests")
public class SauceDemoUiTest extends BaseUiTest {
    @Test
    @DisplayName("UI-001: Standard user completes E2E purchase flow")
    @Story("Standard user completes an order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
        Test Case ID: UI-001
        Title: Standard user completes an end-to-end purchase flow
        Preconditions:
          - Browser is opened and Allure listener is configured
          - User navigates to https://www.saucedemo.com/
          - Credentials for standard_user are available
        
        Steps:
          1. Open the login page
          2. Enter username and password
          3. Click Login and land on the inventory page
          4. Add the first item to the cart
          5. Verify cart badge shows one item
          6. Open the cart and click Checkout
          7. Fill in shipping information (First Name, Last Name, Postal Code)
          8. Click Continue and then Finish
        
        Expected:
          - The confirmation page shows text "Thank you for your order!"
        """)
    void standardUserCanCheckout() {
        var inventory = new LoginPage()
                .logInAs("standard_user", "secret_sauce")
                .addFirstItemToCart();
        int badgeCount = Integer.parseInt(inventory.getCartBadge().getText());

        var checkout = inventory
                .openCart()
                .checkout()
                .fillShippingInfo("Name", "Surname", "123456");

        var confirmation = checkout.finishCheckout();
        String confirmationText = confirmation.getCompleteHeader().getText();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(badgeCount)
                    .as("Cart badge count")
                    .isEqualTo(1);

            softAssertions.assertThat(confirmationText)
                    .as("Order confirmation text")
                    .isEqualTo("Thank you for your order!");
        });
    }
}
