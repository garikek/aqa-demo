package com.software.modsen.demo.pages.playwright.demoqa;

import com.microsoft.playwright.Page;
import com.software.modsen.demo.config.ConfigProvider;
import com.software.modsen.demo.pages.playwright.BasePage;

public abstract class DemoqaBasePage extends BasePage {
    protected static final String BASE_URL = ConfigProvider.APP.getDemoqa().getUrl();

    protected DemoqaBasePage(Page page) {
        super(page);
    }
}
