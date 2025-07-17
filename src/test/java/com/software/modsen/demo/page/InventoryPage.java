package com.software.modsen.demo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryPage {
    private final ElementsCollection inventoryItems = $$(".inventory_item");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement shoppingCartLink = $(".shopping_cart_link");

    public InventoryPage addFirstItemToCart() {
        step("Add first item to cart", () ->
                inventoryItems.first()
                        .$(".btn_inventory")
                        .click()
        );
        return this;
    }

    public InventoryPage assertCartBadgeCount(int expectedCount) {
        step("Assert number of items in cart badge: " + expectedCount, () -> {
            String actual = cartBadge.getText();
            assertThat(actual)
                    .as("Badge count")
                    .isEqualTo(String.valueOf(expectedCount));
        });
        return this;
    }

    public CartPage openCart() {
        step("Open cart page", () ->
                shoppingCartLink.click()
        );
        return new CartPage();
    }
}
