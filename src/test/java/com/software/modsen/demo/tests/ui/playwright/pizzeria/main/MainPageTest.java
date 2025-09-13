package com.software.modsen.demo.tests.ui.playwright.pizzeria.main;

import com.microsoft.playwright.Page;
import com.software.modsen.demo.pages.playwright.pizzeria.main.MainPage;
import com.software.modsen.demo.tests.ui.playwright.BasePlaywrightUiTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MainPageTest extends BasePlaywrightUiTest {
    @Test
    void slidePizzaSlider_shouldShowNewItems() {
        MainPage mainPage = new MainPage(page);
        List<String> beforeSlide = mainPage.getVisiblePizzaItems().allInnerTexts();

        mainPage.slideLeftPizzaSlider(3);
        List<String> afterLeftSlide = mainPage.getVisiblePizzaItems().allInnerTexts();

        mainPage.slideRightPizzaSlider(2);
        List<String> afterRightSlide = mainPage.getVisiblePizzaItems().allInnerTexts();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(beforeSlide)
                    .as("Left slider didn't update element list")
                    .isNotEqualTo(afterLeftSlide);
            softAssertions.assertThat(beforeSlide)
                    .as("Right slider didn't update element list")
                    .isNotEqualTo(afterRightSlide);
        });
    }

    @Test
    void itemImage_redirectsToItemPage() {
        MainPage mainPage = new MainPage(page);
        String itemName = mainPage.getFirstPizzaTitle();

        mainPage.getVisiblePizzaItems().first().click();
        String itemPageTitle = page.title();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(itemPageTitle)
                    .as("Page should contain item name")
                    .contains(itemName);
        });
    }

    @Test
    void scrollToTopButton_appearsWhenScrollDown() {
        MainPage mainPage = new MainPage(page);

        String beforeScrollingDown = mainPage.getScrollToTopButton().getAttribute("style");
        page.mouse().wheel(0, 1000);
        page.waitForTimeout(200);
        String afterScrollingDown = mainPage.getScrollToTopButton().getAttribute("style");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(beforeScrollingDown)
                    .as("")
                    .contains("right: -65px;");
            softAssertions.assertThat(afterScrollingDown)
                    .as("")
                    .contains("right: 20px;");
        });
    }

    @Test
    void socialLinks_openInNewTab() {
        MainPage mainPage = new MainPage(page);
        SoftAssertions softAssertions = new SoftAssertions();

        Page instagramPop = page.waitForPopup(() -> {
            mainPage.getInstagramLink().scrollIntoViewIfNeeded();
            mainPage.getInstagramLink().click();
        });
        instagramPop.waitForLoadState();
        softAssertions.assertThat(instagramPop.url())
                .as("Should navigate to Instagram")
                .contains("instagram.com");
        instagramPop.close();
        page.bringToFront();

        Page facebookPop = page.waitForPopup(() -> {
            mainPage.getFacebookLink().scrollIntoViewIfNeeded();
            mainPage.getFacebookLink().click();
        });
        facebookPop.waitForLoadState();
        softAssertions.assertThat(facebookPop.url())
                .as("Should navigate to Facebook")
                .contains("facebook.com");
        facebookPop.close();
        page.bringToFront();

        Page vkPop = page.waitForPopup(() -> {
            mainPage.getVkLink().scrollIntoViewIfNeeded();
            mainPage.getVkLink().click();
        });
        vkPop.waitForLoadState();
        softAssertions.assertThat(vkPop.url())
                .as("Should navigate to VK")
                .contains("vk.com");
        vkPop.close();

        softAssertions.assertAll();
    }
}
