package com.software.engineering.services.impl;

import com.software.engineering.bo.request.BookRestaurantRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookRestaurantResponse;
import com.software.engineering.entities.RestaurantBookingsEntity;
import com.software.engineering.entities.RestaurantEntity;
import com.software.engineering.entities.UserEntity;
import com.software.engineering.repositories.RestaurantBookingsRepository;
import com.software.engineering.repositories.RestaurantRepository;
import com.software.engineering.repositories.UserRepository;
import com.software.engineering.services.RestaurantBookingsService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Service
public class RestaurantBookingServiceImpl implements RestaurantBookingsService {

    private final RestaurantBookingsRepository restaurantBookingsRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantBookingServiceImpl(RestaurantBookingsRepository restaurantBookingsRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantBookingsRepository = restaurantBookingsRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    @Override
    public BookRestaurantResponse createBooking(BookRestaurantRequest request) {
        preValidate(request);

        RestaurantBookingsEntity restaurantBookings = restaurantBookingsRepository.save(mapCreateRequestToEntity(request));

        return BookRestaurantResponse.builder()
                .id(restaurantBookings.getId())
                .restaurantId(restaurantBookings.getRestaurant().getId())
                .restaurantName(restaurantBookings.getRestaurant().getName())
                .restaurantLocation(restaurantBookings.getRestaurant().getLocation())
                .restaurantPhoneNumber(restaurantBookings.getRestaurant().getPhoneNumber())
                .userId(restaurantBookings.getUser().getId())
                .appointmentTime(restaurantBookings.getAppointmentTime())
                .build();
    }

    @Override
    public AvailableTimeResponse viewAvailableTimes(Long restaurantId, LocalDate date) {
        List<LocalTime> availableTimes = IntStream.rangeClosed(9, 18)
                .mapToObj(i -> LocalTime.of(i, 0))
                .collect(Collectors.toList());

        List<LocalTime> reservedTimes = restaurantBookingsRepository.findAll(Example.of(RestaurantBookingsEntity.builder()
                        .appointmentTime(date.atStartOfDay()).build())).stream()
                .map(RestaurantBookingsEntity::getAppointmentTime)
                .map(LocalDateTime::toLocalTime)
                .collect(Collectors.toList());

        availableTimes.removeAll(reservedTimes);

        return AvailableTimeResponse.builder()
                .availableDates(availableTimes)
                .build();
    }

    @Override
    public List<BookRestaurantResponse> viewBookings(Long userId) {
        if (!userRepository.existsById(userId))
            throw new RuntimeException("No User Found with ID: " + userId);

        return restaurantBookingsRepository.findAll(Example.of(RestaurantBookingsEntity.builder()
                        .user(UserEntity.builder().id(userId).build()).build())).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private void preValidate(BookRestaurantRequest request) {
        if (isNull(request.getRestaurantId()) || isNull(request.getUserId()) || isNull(request.getAppointmentTime()))
            throw new RuntimeException("Please Enter All Fields");

        if (!restaurantRepository.existsById(request.getRestaurantId()))
            throw new RuntimeException("No Restaurant Found with ID: " + request.getRestaurantId());

        if (!userRepository.existsById(request.getUserId()))
            throw new RuntimeException("No User Found with ID: " + request.getUserId());
    }

    private RestaurantBookingsEntity mapCreateRequestToEntity(BookRestaurantRequest request) {
        return RestaurantBookingsEntity.builder()
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .restaurant(RestaurantEntity.builder().id(request.getRestaurantId()).build())
                .user(UserEntity.builder().id(request.getUserId()).build())
                .appointmentTime(request.getAppointmentTime())
                .build();
    }

    private BookRestaurantResponse mapEntityToResponse(RestaurantBookingsEntity entity) {
        return BookRestaurantResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .restaurantId(entity.getRestaurant().getId())
                .restaurantName(entity.getRestaurant().getName())
                .restaurantLocation(entity.getRestaurant().getLocation())
                .restaurantPhoneNumber(entity.getRestaurant().getPhoneNumber())
                .appointmentTime(entity.getAppointmentTime())
                .build();
    }
}
