package com.software.modsen.demo.tests.ui.playwright.saucedemo;

import com.software.modsen.demo.pages.playwright.saucedemo.auth.LoginPage;
import com.software.modsen.demo.pages.playwright.saucedemo.cart.CartPage;
import com.software.modsen.demo.pages.playwright.saucedemo.checkout.CheckoutCompletePage;
import com.software.modsen.demo.pages.playwright.saucedemo.checkout.CheckoutOverviewPage;
import com.software.modsen.demo.pages.playwright.saucedemo.inventory.InventoryPage;
import com.software.modsen.demo.tests.ui.playwright.BasePlaywrightUiTest;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Feature("E2E Purchase Flow")
@DisplayName("SauceDemo UI Tests (Playwright)")
@ExtendWith(AllureJunit5.class)
class SaucedemoTest extends BasePlaywrightUiTest {
    @Test
    @DisplayName("UI-002: Invalid login shows error")
    @Story("User attempts login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Test Case ID: UI-002
            Title: Invalid login attempt displays an appropriate error message
            Preconditions:
              - Browser is open and Allure listener is configured
              - User is on the SauceDemo login page
                    
            Steps:
              1. Navigate to login page
              2. Enter invalid username and password
              3. Click Login
                    
            Expected:
              - Error message "Username and password do not match" is displayed
            """)
    void loginWithInvalidCredentials_displaysErrorMessage() {
        LoginPage loginPage = new LoginPage(page);

        loginPage.logInAs("invalid_user", "wrong_password");
        String errorMessage = loginPage.getErrorMessage();

        assertSoftly(softAssertions -> softAssertions.assertThat(errorMessage)
                .as("Error message for invalid credentials")
                .isNotBlank()
                .contains("Username and password do not match"));
    }

    @Test
    @DisplayName("UI-001: Standard user completes E2E purchase flow")
    @Story("Standard user completes an order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Test Case ID: UI-001
            Title: Standard user completes an end-to-end purchase flow
            Preconditions:
              - Browser is open and Allure listener is configured
              - User navigates to SauceDemo and logs in as standard_user
                    
            Steps:
              1. Login as standard_user / secret_sauce
              2. Verify cart is empty
              3. Add first item to cart
              4. Verify cart badge shows 1 item
              5. Open the cart and click Checkout
              6. Fill shipping info (First Name, Last Name, Postal Code)
              7. Click Continue and then Finish
              8. Return to products page
                    
            Expected:
              - "Item total", "Tax" and "Total" labels appear on overview
              - Confirmation page shows "Thank you for your order!"
              - Cart is cleared after completing the order
            """)
    void completeE2ePurchaseFlowAsStandardUser_clearsCartAndShowsConfirmation() {
        InventoryPage inventory = new LoginPage(page)
                .logInAs("standard_user", "secret_sauce");
        int itemCountAfterLogin = inventory.getCartItemCount();

        inventory.addFirstItemToCart();
        int itemCountAfterAddingItem = inventory.getCartItemCount();

        CartPage cart = inventory.goToCart();
        int itemCountOnCartPage = cart.getNumberOfItemsInCart();

        CheckoutOverviewPage overview = cart
                .proceedToCheckout()
                .fillCustomerInfo("John", "Doe", "123456")
                .clickContinue();
        String itemTotalPrice = overview.getItemTotal();
        String taxTotal = overview.getTax();
        String priceTotal = overview.getPriceTotal();

        CheckoutCompletePage complete = overview.clickFinish();
        String completeHeader = complete.getCompleteHeader();
        String completeText = complete.getCompleteText();

        InventoryPage backHome = complete.clickBackHome();
        int itemCountAfterComplete = backHome.getCartItemCount();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(itemCountAfterLogin)
                    .as("Items in cart after login (before adding)")
                    .isZero();
            softAssertions.assertThat(itemCountAfterAddingItem)
                    .as("Items in cart after adding first item")
                    .isEqualTo(1);
            softAssertions.assertThat(itemCountOnCartPage)
                    .as("Items in cart on CartPage")
                    .isEqualTo(1);
            softAssertions.assertThat(itemTotalPrice)
                    .as("Item total label on OverviewPage")
                    .contains("Item total");
            softAssertions.assertThat(taxTotal)
                    .as("Tax label on OverviewPage")
                    .contains("Tax");
            softAssertions.assertThat(priceTotal)
                    .as("Total label on OverviewPage")
                    .contains("Total");
            softAssertions.assertThat(completeHeader)
                    .as("Completion header text")
                    .isEqualTo("Thank you for your order!");
            softAssertions.assertThat(completeText)
                    .as("Completion body text")
                    .contains("Your order has been dispatched");
            softAssertions.assertThat(itemCountAfterComplete)
                    .as("Cart cleared after finishing order")
                    .isZero();
        });
    }
}
