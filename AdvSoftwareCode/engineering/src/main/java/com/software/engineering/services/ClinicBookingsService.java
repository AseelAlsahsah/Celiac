package com.software.engineering.services;

import com.software.engineering.bo.request.BookClinicRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookClinicResponse;

import java.time.LocalDate;
import java.util.List;

public interface ClinicBookingsService {

    BookClinicResponse createBooking(BookClinicRequest request);

    AvailableTimeResponse viewAvailableTimes(Long clinicId, LocalDate date);

    List<BookClinicResponse> viewBookings(Long userId);
}
