package com.software.modsen.demo.tests.api.playwright.booker;

import com.microsoft.playwright.APIResponse;
import com.software.modsen.demo.data.BookingDataFactory;
import com.software.modsen.demo.dto.request.Booking;
import com.software.modsen.demo.dto.response.BookingResponse;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.software.modsen.demo.helper.JsonMapper.toJson;
import static io.qameta.allure.Allure.*;

@Epic("Booking API")
@Feature("Booking Endpoints")
@DisplayName("Booking API Tests")
class BookingClientApiTest extends BookingClientBaseTest {

    private static Stream<Booking> bookingProvider() {
        return BookingDataFactory.randomBookings(5).stream();
    }

    private static Stream<Arguments> bookingPairsProvider() {
        return BookingDataFactory.randomBookings(5).stream()
                .map(booking -> Arguments.of(booking, BookingDataFactory.randomBooking()));
    }

    @ParameterizedTest(name = "#{index} createBooking")
    @MethodSource("bookingProvider")
    @DisplayName("Create booking with random data")
    @Story("Create Booking")
    @Severity(SeverityLevel.CRITICAL)
    void createBooking_withRandomBooking_shouldReturnBookingResponse(Booking payload) {
        parameter("Booking payload", toJson(payload));

        BookingResponse response = step("POST /booking", () -> client.createBooking(payload));

        step("Verify booking ID and payload", () -> SoftAssertions
                .assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.bookingid())
                            .as("Booking ID is generated")
                            .isPositive();
                    softAssertions.assertThat(response.booking())
                            .as("Returned payload matches request")
                            .isEqualTo(payload);
                })
        );

        step("Attach full BookingResponse", () -> addAttachment(
                "BookingResponse",
                "application/json",
                toJson(response),
                ".json"
        ));
    }

    @ParameterizedTest(name = "#{index} getBooking")
    @MethodSource("bookingProvider")
    @DisplayName("Retrieve an existing booking")
    @Story("Retrieve Booking")
    @Severity(SeverityLevel.CRITICAL)
    void getBooking_withExistingBooking_shouldReturnBooking(Booking payload) {
        parameter("Original payload", toJson(payload));

        BookingResponse created = step("POST /booking to create", () -> client.createBooking(payload));
        int bookingId = created.bookingid();
        step("Record bookingId", () -> parameter("bookingId", bookingId));

        Booking fetched = step("GET /booking/{id}", () -> client.getBooking(bookingId));

        step("Validate fetched booking", () -> SoftAssertions
                .assertSoftly(softAssertions -> softAssertions.assertThat(fetched)
                        .as("Fetched booking matches original")
                        .isEqualTo(payload))
        );

        step("Cleanup: DELETE /booking/{id}", () -> client.deleteBooking(bookingId));
    }

    @ParameterizedTest(name = "#{index} updateBooking")
    @MethodSource("bookingPairsProvider")
    @DisplayName("Update a booking with valid data")
    @Story("Update Booking")
    @Severity(SeverityLevel.NORMAL)
    void updateBooking_withValidBooking_shouldReturnUpdatedBookingResponse(Booking original, Booking updatePayload) {
        parameter("Original payload", toJson(original));
        parameter("Update payload", toJson(updatePayload));

        BookingResponse created = step("POST /booking to create original", () -> client.createBooking(original));
        int bookingId = created.bookingid();

        Booking updated = step("PUT /booking/{id}", () -> client.updateBooking(bookingId, updatePayload));

        step("Validate updated booking", () -> SoftAssertions
                .assertSoftly(softAssertions -> softAssertions.assertThat(updated)
                        .as("Updated booking payload")
                        .isEqualTo(updatePayload))
        );

        step("Cleanup: DELETE /booking/{id}", () -> client.deleteBooking(bookingId));
    }

    @ParameterizedTest(name = "#{index} deleteBooking")
    @MethodSource("bookingProvider")
    @DisplayName("Delete an existing booking")
    @Story("Delete Booking")
    @Severity(SeverityLevel.NORMAL)
    void deleteBooking_withExistingBooking_shouldReturnNoContentStatus(Booking payload) {
        parameter("Booking to delete", toJson(payload));

        int bookingId = step("POST /booking to create", () -> client.createBooking(payload).bookingid());

        APIResponse deleteResponse = step("DELETE /booking/{id}", () -> client.deleteBooking(bookingId));

        step("Validate delete status", () -> SoftAssertions
                .assertSoftly(softAssertions -> softAssertions.assertThat(deleteResponse.status())
                        .as("Status code indicates success")
                        .isIn(200, 201, 204))
        );

        addAttachment(
                "Delete response body",
                "text/plain",
                deleteResponse.text()
        );
    }
}
