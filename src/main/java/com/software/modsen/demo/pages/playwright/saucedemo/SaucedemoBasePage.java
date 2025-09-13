package com.software.modsen.demo.pages.playwright.saucedemo;

import com.microsoft.playwright.Page;
import com.software.modsen.demo.config.ConfigProvider;
import com.software.modsen.demo.pages.playwright.BasePage;

public abstract class SaucedemoBasePage extends BasePage {
    protected static final String BASE_URL = ConfigProvider.APP.getSaucedemo().getUrl();

    protected SaucedemoBasePage(Page page) {
        super(page);
    }
}
