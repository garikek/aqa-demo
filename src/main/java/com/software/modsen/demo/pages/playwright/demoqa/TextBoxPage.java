package com.software.modsen.demo.pages.playwright.demoqa;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

@Getter
public class TextBoxPage extends DemoqaBasePage {
    private final Locator fullNameInput;
    private final Locator emailInput;
    private final Locator currentAddressInput;
    private final Locator permanentAddressInput;
    private final Locator submitButton;

    private final Locator outputName;
    private final Locator outputEmail;
    private final Locator outputCurrentAddress;
    private final Locator outputPermanentAddress;

    public TextBoxPage(Page page) {
        super(page);
        page.navigate(BASE_URL + "/text-box");

        fullNameInput = page.locator("#userName");
        emailInput = page.locator("#userEmail");
        currentAddressInput = page.locator("#currentAddress");
        permanentAddressInput = page.locator("#permanentAddress");
        submitButton = page.locator("#submit");

        outputName = page.locator("#output #name");
        outputEmail = page.locator("#output #email");
        outputCurrentAddress = page.locator("#output #currentAddress");
        outputPermanentAddress = page.locator("#output #permanentAddress");
    }

    public TextBoxPage setFullName(String name) {
        fill(fullNameInput, name);
        return this;
    }

    public TextBoxPage setEmail(String email) {
        fill(emailInput, email);
        return this;
    }

    public TextBoxPage setCurrentAddress(String address) {
        fill(currentAddressInput, address);
        return this;
    }

    public TextBoxPage setPermanentAddress(String address) {
        fill(permanentAddressInput, address);
        return this;
    }

    public TextBoxPage clickSubmit() {
        click(submitButton);
        return this;
    }

    public String getNameOutput() {
        return getText(outputName)
                .replaceFirst("^Name:\\s*", "")
                .trim();
    }

    public String getEmailOutput() {
        return getText(outputEmail)
                .replaceFirst("^Email:\\s*", "")
                .trim();
    }

    public String getCurrentAddressOutput() {
        return getText(outputCurrentAddress)
                .replaceFirst("^Current Address[\\s:]*", "")
                .trim();
    }

    public String getPermanentAddressOutput() {
        return getText(outputPermanentAddress)
                .replaceFirst("^Permananet Address[\\s:]*", "")
                .trim();
    }
}
