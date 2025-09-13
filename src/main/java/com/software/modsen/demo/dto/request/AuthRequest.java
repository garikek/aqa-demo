package com.software.modsen.demo.dto.request;

import lombok.Builder;

@Builder
public record AuthRequest(
        String username,
        String password
) {
}
