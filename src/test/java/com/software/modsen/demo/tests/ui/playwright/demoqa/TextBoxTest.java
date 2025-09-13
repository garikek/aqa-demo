package com.software.modsen.demo.tests.ui.playwright.demoqa;

import com.software.modsen.demo.data.TextBoxDataFactory;
import com.software.modsen.demo.dto.request.TextBox;
import com.software.modsen.demo.pages.playwright.demoqa.TextBoxPage;
import com.software.modsen.demo.tests.ui.playwright.BasePlaywrightUiTest;
import io.qameta.allure.junit5.AllureJunit5;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("DemoQA text box ui test")
@ExtendWith(AllureJunit5.class)
class TextBoxTest extends BasePlaywrightUiTest {
    static Stream<TextBox> randomTextBoxesProvider() {
        List<TextBox> boxes = TextBoxDataFactory.randomTextBoxes(5);
        return boxes.stream();
    }

    @ParameterizedTest(name = "Run #{index} with data={0}")
    @MethodSource("randomTextBoxesProvider")
    void submitTextBoxForm_withRandomData_shouldMatchOutputs(TextBox data) {
        TextBoxPage textBoxPage = new TextBoxPage(page);

        textBoxPage
                .setFullName(data.fullName())
                .setEmail(data.email())
                .setCurrentAddress(data.currentAddress())
                .setPermanentAddress(data.permanentAddress())
                .clickSubmit();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(textBoxPage.getNameOutput())
                    .as("Name output")
                    .isEqualTo(data.fullName());
            softly.assertThat(textBoxPage.getEmailOutput())
                    .as("Email output")
                    .isEqualTo(data.email());
            softly.assertThat(textBoxPage.getCurrentAddressOutput())
                    .as("Current Address output")
                    .isEqualTo(data.currentAddress());
            softly.assertThat(textBoxPage.getPermanentAddressOutput())
                    .as("Permanent Address output")
                    .isEqualTo(data.permanentAddress());
        });
    }
}
