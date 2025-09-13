package com.software.modsen.demo.pages.playwright.saucedemo.inventory;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import com.software.modsen.demo.pages.playwright.saucedemo.cart.CartPage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class InventoryPage extends SaucedemoBasePage {
    private final Locator inventoryItems;
    private final Locator cartLink;
    private final Locator cartBadge;
    private final Locator menuButton;
    private final Locator logoutLink;

    public InventoryPage(Page page) {
        super(page);
        inventoryItems = page.locator(".inventory_item");
        cartLink = page.locator(".shopping_cart_link");
        cartBadge = page.locator(".shopping_cart_badge");
        menuButton = page.locator("#react-burger-menu-btn");
        logoutLink = page.locator("#logout_sidebar_link");
    }

    public InventoryPage addFirstItemToCart() {
        step("Add first item to cart", () ->
                clickFirstChild(inventoryItems, ".btn_inventory"));
        return this;
    }

    public int getCartItemCount() {
        return isVisible(cartBadge)
                ? Integer.parseInt(getText(cartBadge))
                : 0;
    }

    public CartPage goToCart() {
        step("Go to cart page", () -> click(cartLink));
        return new CartPage(page);
    }
}
