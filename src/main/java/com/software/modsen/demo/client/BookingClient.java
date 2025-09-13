package com.software.modsen.demo.client;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.software.modsen.demo.config.ConfigProvider;
import com.software.modsen.demo.dto.request.AuthRequest;
import com.software.modsen.demo.dto.request.Booking;
import com.software.modsen.demo.dto.response.BookerAuthResponse;
import com.software.modsen.demo.dto.response.BookingResponse;
import lombok.RequiredArgsConstructor;

import static com.software.modsen.demo.helper.JsonMapper.fromJson;
import static com.software.modsen.demo.helper.JsonMapper.toJson;

@RequiredArgsConstructor
public class BookingClient {
    private final APIRequestContext apiRequestContext;
    private static final String BASE_PATH = ConfigProvider.API.getRestfulBooker().getBaseApiPath();
    private static final String DEFAULT_USERNAME = ConfigProvider.API.getRestfulBooker().getDefaultUsername();
    private static final String DEFAULT_PASSWORD = ConfigProvider.API.getRestfulBooker().getDefaultPassword();

    public BookerAuthResponse getToken() {
        AuthRequest authRequest = new AuthRequest(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        String payload = toJson(authRequest);
        APIResponse response = apiRequestContext.post(
                "/auth",
                RequestOptions.create().setData(payload)
        );
        return fromJson(response, BookerAuthResponse.class);
    }

    public BookingResponse createBooking(Booking booking) {
        String payload = toJson(booking);
        APIResponse response = apiRequestContext.post(
                BASE_PATH,
                RequestOptions.create().setData(payload)
        );
        return fromJson(response, BookingResponse.class);
    }

    public Booking getBooking(int id) {
        APIResponse response = apiRequestContext.get(BASE_PATH + "/" + id);
        return fromJson(response, Booking.class);
    }

    public Booking updateBooking(int id, Booking booking) {
        String token = getToken().token();
        String payload = toJson(booking);
        APIResponse response = apiRequestContext.put(
                BASE_PATH + "/" + id,
                RequestOptions.create()
                        .setData(payload)
                        .setHeader("Cookie", "token=" + token)
        );
        return fromJson(response, Booking.class);
    }

    public APIResponse deleteBooking(int id) {
        String token = getToken().token();
        return apiRequestContext.delete(
                BASE_PATH + "/" + id,
                RequestOptions.create()
                        .setHeader("Cookie", "token=" + token)
        );
    }
}
