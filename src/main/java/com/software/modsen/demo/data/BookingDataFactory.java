package com.software.modsen.demo.data;

import com.software.modsen.demo.dto.request.Booking;
import com.software.modsen.demo.dto.request.BookingDates;

import java.time.LocalDate;
import java.util.List;

public class BookingDataFactory extends AbstractDataFactory {
    public static Booking randomBooking() {
        String checkin  = LocalDate.now().minusDays(1).toString();
        String checkout = LocalDate.now().plusDays(1).toString();

        return Booking.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(50, 500))
                .depositpaid(faker.bool().bool())
                .bookingdates(
                        BookingDates.builder()
                                .checkin(checkin)
                                .checkout(checkout)
                                .build()
                )
                .additionalneeds(faker.options()
                        .option("Breakfast", "Late Checkout", "Airport Transfer"))
                .build();
    }

    public static List<Booking> randomBookings(int count) {
        return randomList(BookingDataFactory::randomBooking, count);
    }
}
