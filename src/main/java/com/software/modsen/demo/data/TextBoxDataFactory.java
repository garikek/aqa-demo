package com.software.modsen.demo.data;

import com.software.modsen.demo.dto.request.TextBox;

import java.util.List;

public class TextBoxDataFactory extends AbstractDataFactory {
    public static TextBox uniqueTextBox() {
        return TextBox.builder()
                .fullName(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .currentAddress(faker.address().streetAddress())
                .permanentAddress(faker.address().secondaryAddress())
                .build();
    }

    public static List<TextBox> randomTextBoxes(int count) {
        return randomList(TextBoxDataFactory::uniqueTextBox, count);
    }
}
