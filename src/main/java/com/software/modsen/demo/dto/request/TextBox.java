package com.software.modsen.demo.dto.request;

import lombok.Builder;

@Builder
public record TextBox(
        String fullName,
        String email,
        String currentAddress,
        String permanentAddress
) {
}
