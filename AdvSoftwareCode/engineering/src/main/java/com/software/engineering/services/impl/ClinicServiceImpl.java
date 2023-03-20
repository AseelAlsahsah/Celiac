package com.software.engineering.services.impl;

import com.software.engineering.bo.request.CreateClinicRequest;
import com.software.engineering.bo.response.ClinicResponse;
import com.software.engineering.entities.ClinicEntity;
import com.software.engineering.entities.UserEntity;
import com.software.engineering.entities.UserRoles;
import com.software.engineering.repositories.ClinicRepository;
import com.software.engineering.repositories.UserRepository;
import com.software.engineering.services.ClinicService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository, UserRepository userRepository) {
        this.clinicRepository = clinicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ClinicResponse createClinic(CreateClinicRequest request) {
        preValidateCreateRequest(request);

        ClinicEntity clinic = clinicRepository.save(mapCreateRequestToEntity(request));

        return ClinicResponse.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .location(clinic.getLocation())
                .phoneNumber(clinic.getPhoneNumber())
                .creditCard(clinic.getCreditCard())
                .build();
    }

    @Override
    public List<ClinicResponse> viewClinics() {
        return clinicRepository.findAll().stream()
                .map(this::mapClinicEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicResponse> searchClinics(String name) {
        if (isNull(name) || name.trim().isEmpty())
            throw new RuntimeException("Please Enter A Name");

        return clinicRepository.findAllByNameLike(name).stream()
                .map(this::mapClinicEntityToResponse)
                .collect(Collectors.toList());
    }

    private void preValidateCreateRequest(CreateClinicRequest request) {
        if (isNull(request.getUserId()) || isNull(request.getName()) || isNull(request.getLocation()) || isNull(request.getPhoneNumber()) || isNull(request.getCreditCard()))
            throw new RuntimeException("Please Enter All Fields");

        userRepository.findOne(Example.of(UserEntity.builder()
                .id(request.getUserId()).role(UserRoles.ADMIN).build()))
                .orElseThrow(() -> new RuntimeException("No Admin User found with ID:" + request.getUserId()));
    }

    private ClinicEntity mapCreateRequestToEntity(CreateClinicRequest request) {
        return ClinicEntity.builder()
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .name(request.getName())
                .location(request.getLocation())
                .phoneNumber(request.getPhoneNumber())
                .creditCard(request.getCreditCard())
                .build();
    }

    private ClinicResponse mapClinicEntityToResponse(ClinicEntity entity) {
        return ClinicResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .phoneNumber(entity.getPhoneNumber())
                .creditCard(entity.getCreditCard())
                .build();
    }
}
