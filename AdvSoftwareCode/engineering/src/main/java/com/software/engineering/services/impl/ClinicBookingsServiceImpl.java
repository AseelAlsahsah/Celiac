package com.software.engineering.services.impl;

import com.software.engineering.bo.request.BookClinicRequest;
import com.software.engineering.bo.response.AvailableTimeResponse;
import com.software.engineering.bo.response.BookClinicResponse;
import com.software.engineering.entities.ClinicBookingsEntity;
import com.software.engineering.entities.ClinicEntity;
import com.software.engineering.entities.UserEntity;
import com.software.engineering.repositories.ClinicBookingsRepository;
import com.software.engineering.repositories.ClinicRepository;
import com.software.engineering.repositories.UserRepository;
import com.software.engineering.services.ClinicBookingsService;
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
public class ClinicBookingsServiceImpl implements ClinicBookingsService {

    private final ClinicBookingsRepository clinicBookingsRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public ClinicBookingsServiceImpl(ClinicBookingsRepository clinicBookingsRepository, ClinicRepository clinicRepository, UserRepository userRepository) {
        this.clinicBookingsRepository = clinicBookingsRepository;
        this.clinicRepository = clinicRepository;
        this.userRepository = userRepository;
    }


    @Override
    public BookClinicResponse createBooking(BookClinicRequest request) {
        preValidate(request);

        ClinicBookingsEntity clinicBookings = clinicBookingsRepository.save(mapCreateRequestToEntity(request));

        return BookClinicResponse.builder()
                .id(clinicBookings.getId())
                .clinicId(clinicBookings.getClinic().getId())
                .clinicName(clinicBookings.getClinic().getName())
                .clinicLocation(clinicBookings.getClinic().getLocation())
                .clinicPhoneNumber(clinicBookings.getClinic().getPhoneNumber())
                .userId(clinicBookings.getId())
                .appointmentTime(clinicBookings.getAppointmentTime())
                .build();
    }

    @Override
    public AvailableTimeResponse viewAvailableTimes(Long clinicId, LocalDate date) {
        List<LocalTime> availableTimes = IntStream.rangeClosed(9, 18)
                .mapToObj(i -> LocalTime.of(i, 0))
                .collect(Collectors.toList());

        List<LocalTime> reservedTimes = clinicBookingsRepository.findAll(Example.of(ClinicBookingsEntity.builder()
                        .appointmentTime(date.atStartOfDay()).build())).stream()
                .map(ClinicBookingsEntity::getAppointmentTime)
                .map(LocalDateTime::toLocalTime)
                .collect(Collectors.toList());

        availableTimes.removeAll(reservedTimes);

        return AvailableTimeResponse.builder()
                .availableDates(availableTimes)
                .build();
    }

    @Override
    public List<BookClinicResponse> viewBookings(Long userId) {
        if (!userRepository.existsById(userId))
            throw new RuntimeException("No User Found with ID: " + userId);

        return clinicBookingsRepository.findAll(Example.of(ClinicBookingsEntity.builder()
                        .user(UserEntity.builder().id(userId).build()).build())).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private void preValidate(BookClinicRequest request) {
        if (isNull(request.getClinicId()) || isNull(request.getUserId()) || isNull(request.getAppointmentTime()))
            throw new RuntimeException("Please Enter All Fields");

        if (!clinicRepository.existsById(request.getClinicId()))
            throw new RuntimeException("No Clinic Found with ID: " + request.getClinicId());

        if (!userRepository.existsById(request.getUserId()))
            throw new RuntimeException("No User Found with ID: " + request.getUserId());
    }

    private ClinicBookingsEntity mapCreateRequestToEntity(BookClinicRequest request) {
        return ClinicBookingsEntity.builder()
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .clinic(ClinicEntity.builder().id(request.getClinicId()).build())
                .user(UserEntity.builder().id(request.getUserId()).build())
                .appointmentTime(request.getAppointmentTime())
                .build();
    }

    private BookClinicResponse mapEntityToResponse(ClinicBookingsEntity entity) {
        return BookClinicResponse.builder()
                .id(entity.getId())
                .clinicId(entity.getClinic().getId())
                .userId(entity.getUser().getId())
                .clinicName(entity.getClinic().getName())
                .clinicLocation(entity.getClinic().getLocation())
                .clinicPhoneNumber(entity.getClinic().getPhoneNumber())
                .appointmentTime(entity.getAppointmentTime())
                .build();
    }
}
