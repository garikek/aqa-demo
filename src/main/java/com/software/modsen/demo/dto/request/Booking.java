package com.software.modsen.demo.dto.request;

import lombok.Builder;

@Builder
public record Booking(
        String firstname,
        String lastname,
        int totalprice,
        boolean depositpaid,
        BookingDates bookingdates,
        String additionalneeds
) {
}
