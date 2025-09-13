package com.software.modsen.demo.pages.playwright.pizzeria;

import com.microsoft.playwright.Page;
import com.software.modsen.demo.config.ConfigProvider;
import com.software.modsen.demo.pages.playwright.BasePage;

public abstract class PizzeriaBasePage extends BasePage {
    protected static final String BASE_URL = ConfigProvider.APP.getPizzeria().getUrl();

    protected PizzeriaBasePage(Page page) {
        super(page);
    }
}
