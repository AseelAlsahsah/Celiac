package com.software.engineering.controllers;

import com.software.engineering.bo.request.BookClinicRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookClinicResponse;
import com.software.engineering.services.ClinicBookingsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic-bookings")
public class ClinicBookingsController {

    private final ClinicBookingsService clinicBookingsService;

    public ClinicBookingsController(ClinicBookingsService clinicBookingsService) {
        this.clinicBookingsService = clinicBookingsService;
    }

    @PostMapping
    public ResponseEntity<BookClinicResponse> createBooking(@RequestBody BookClinicRequest request) {
        return new ResponseEntity<>(clinicBookingsService.createBooking(request), HttpStatus.OK);
    }

    @GetMapping("/userId")
    public ResponseEntity<List<BookClinicResponse>> viewBookings(@PathVariable Long userId) {
        return new ResponseEntity<>(clinicBookingsService.viewBookings(userId), HttpStatus.OK);
    }

    @GetMapping("/times")
    public ResponseEntity<AvailableTimeResponse> viewAvailableTimes(@RequestParam Long clinicId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(clinicBookingsService.viewAvailableTimes(clinicId, date), HttpStatus.OK);
    }
}
