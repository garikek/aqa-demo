package com.software.modsen.demo.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

@Getter
public class ConfirmationPage {
    private final SelenideElement completeHeader = $(".complete-header");
}
