package com.software.modsen.demo.pages.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BasePage {
    protected final Page page;

    protected void click(Locator locator) {
        locator.click();
    }

    protected void fill(Locator locator, String text) {
        locator.fill(text);
    }

    protected String getText(Locator locator) {
        return locator.textContent();
    }

    protected int getCount(Locator locator) {
        return locator.count();
    }

    protected void clickFirstChild(Locator parent, String childCssSelector) {
        parent
                .first()
                .locator(childCssSelector)
                .click();
    }

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }
}
