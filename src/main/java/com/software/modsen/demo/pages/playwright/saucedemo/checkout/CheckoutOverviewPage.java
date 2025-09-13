package com.software.modsen.demo.pages.playwright.saucedemo.checkout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.saucedemo.SaucedemoBasePage;
import lombok.Getter;

import static io.qameta.allure.Allure.step;

@Getter
public class CheckoutOverviewPage extends SaucedemoBasePage {
    private final Locator itemTotalLabel;
    private final Locator taxLabel;
    private final Locator priceTotalLabel;
    private final Locator finishButton;

    public CheckoutOverviewPage(Page page) {
        super(page);
        itemTotalLabel = page.locator(".summary_subtotal_label");
        taxLabel = page.locator(".summary_tax_label");
        priceTotalLabel = page.locator(".summary_total_label");
        finishButton = page.locator("#finish");
    }

    public String getItemTotal() {
        return getText(itemTotalLabel);
    }

    public String getTax() {
        return getText(taxLabel);
    }

    public String getPriceTotal() {
        return getText(priceTotalLabel);
    }

    public CheckoutCompletePage clickFinish() {
        step("Click finish button", () -> click(finishButton));
        return new CheckoutCompletePage(page);
    }
}
