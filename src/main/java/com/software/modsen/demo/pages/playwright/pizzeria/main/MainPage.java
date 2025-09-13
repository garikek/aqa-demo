package com.software.modsen.demo.pages.playwright.pizzeria.main;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.software.modsen.demo.pages.playwright.pizzeria.PizzeriaBasePage;
import lombok.Getter;

@Getter
public class MainPage extends PizzeriaBasePage {
    private final Locator pizzaSliderContainer;
    private final Locator pizzaPrevSlick;
    private final Locator pizzaNextSlick;
    private final Locator visiblePizzaItems;
    private final Locator scrollToTopButton;
    private final Locator instagramLink;
    private final Locator facebookLink;
    private final Locator vkLink;

    public MainPage(Page page) {
        super(page);
        page.navigate(BASE_URL);

        pizzaSliderContainer = page.locator("ul.new-prod-slide").first();
        pizzaPrevSlick = page.locator(".slick-prev");
        pizzaNextSlick = page.locator(".slick-next");
        visiblePizzaItems = page.locator("ul.new-prod-slide li[aria-hidden='false']");
        scrollToTopButton = page.locator("#ak-top");
        instagramLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Instagram"));
        facebookLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Facebook"));
        vkLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("ВКонтакте"));
    }

    public MainPage slideLeftPizzaSlider(int times) {
        for (int i = 0; i < times; i++) {
            pizzaSliderContainer.hover();
            pizzaPrevSlick.click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(500);
        }
        return this;
    }

    public MainPage slideRightPizzaSlider(int times) {
        for (int i = 0; i < times; i++) {
            pizzaSliderContainer.hover();
            pizzaNextSlick.click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(500);
        }
        return this;
    }

    public String getFirstPizzaTitle() {
        return visiblePizzaItems
                .first()
                .locator("div.item-img > a")
                .first()
                .getAttribute("title");
    }
}
