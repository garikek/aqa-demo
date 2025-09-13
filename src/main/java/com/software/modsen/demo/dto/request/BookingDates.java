package com.software.modsen.demo.dto.request;

import lombok.Builder;

@Builder
public record BookingDates(
        String checkin,
        String checkout
) {
}
