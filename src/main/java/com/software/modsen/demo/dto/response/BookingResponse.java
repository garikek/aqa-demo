package com.software.modsen.demo.dto.response;

import com.software.modsen.demo.dto.request.Booking;
import lombok.Builder;

@Builder
public record BookingResponse(
        int bookingid,
        Booking booking
) {
}
