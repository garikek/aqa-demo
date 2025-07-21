package com.software.modsen.demo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

@Getter
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

    public CartPage openCart() {
        step("Open cart page", () ->
                shoppingCartLink.click()
        );
        return new CartPage();
    }
}
