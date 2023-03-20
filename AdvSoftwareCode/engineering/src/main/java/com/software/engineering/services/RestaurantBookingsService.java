package com.software.engineering.services;

import com.software.engineering.bo.request.BookRestaurantRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookRestaurantResponse;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantBookingsService {

    BookRestaurantResponse createBooking(BookRestaurantRequest request);

    AvailableTimeResponse viewAvailableTimes(Long restaurantId, LocalDate date);

    List<BookRestaurantResponse> viewBookings(Long userId);
}
