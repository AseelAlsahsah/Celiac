package com.software.engineering.controllers;

import com.software.engineering.bo.request.BookRestaurantRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookRestaurantResponse;
import com.software.engineering.services.RestaurantBookingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-bookings")
public class RestaurantBookingsController {

    private final RestaurantBookingsService restaurantBookingsService;

    public RestaurantBookingsController(RestaurantBookingsService restaurantBookingsService) {
        this.restaurantBookingsService = restaurantBookingsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookRestaurantResponse> createBooking(@RequestBody BookRestaurantRequest request) {
        return new ResponseEntity<>(restaurantBookingsService.createBooking(request), HttpStatus.OK);
    }

    @GetMapping("/userId")
    public ResponseEntity<List<BookRestaurantResponse>> viewBookings(@PathVariable Long userId) {
        return new ResponseEntity<>(restaurantBookingsService.viewBookings(userId), HttpStatus.OK);
    }

    @GetMapping("/times")
    public ResponseEntity<AvailableTimeResponse> viewAvailableTimes(@RequestParam Long clinicId, @RequestParam LocalDate date) {
        return new ResponseEntity<>(restaurantBookingsService.viewAvailableTimes(clinicId, date), HttpStatus.OK);
    }
}