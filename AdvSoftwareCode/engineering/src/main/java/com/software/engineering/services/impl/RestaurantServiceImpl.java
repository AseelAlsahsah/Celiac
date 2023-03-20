package com.software.engineering.services.impl;

import com.software.engineering.bo.request.CreateRestaurantRequest;
import com.software.engineering.bo.response.RestaurantResponse;
import com.software.engineering.entities.RestaurantEntity;
import com.software.engineering.entities.UserEntity;
import com.software.engineering.entities.UserRoles;
import com.software.engineering.repositories.RestaurantRepository;
import com.software.engineering.repositories.UserRepository;
import com.software.engineering.services.RestaurantService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        preValidateCreateRequest(request);

        RestaurantEntity restaurant = restaurantRepository.save(mapCreateRequestToEntity(request));

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .phoneNumber(restaurant.getPhoneNumber())
                .creditCard(restaurant.getCreditCard())
                .build();
    }

    @Override
    public List<RestaurantResponse> viewRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponse> searchRestaurants(String name) {
        if (isNull(name) || name.trim().isEmpty())
            throw new RuntimeException("Please Enter A Name");

        return restaurantRepository.findAllByNameLike(name).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private void preValidateCreateRequest(CreateRestaurantRequest request) {
        if (isNull(request.getUserId()) || isNull(request.getName()) || isNull(request.getLocation()) || isNull(request.getPhoneNumber()) || isNull(request.getCreditCard()))
            throw new RuntimeException("Please Enter All Fields");

        userRepository.findOne(Example.of(UserEntity.builder()
                        .id(request.getUserId()).role(UserRoles.ADMIN).build()))
                .orElseThrow(() -> new RuntimeException("No Admin User found with ID:" + request.getUserId()));
    }

    private RestaurantEntity mapCreateRequestToEntity(CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .name(request.getName())
                .location(request.getLocation())
                .phoneNumber(request.getPhoneNumber())
                .creditCard(request.getCreditCard())
                .build();
    }

    private RestaurantResponse mapEntityToResponse(RestaurantEntity entity) {
        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .phoneNumber(entity.getPhoneNumber())
                .creditCard(entity.getCreditCard())
                .build();
    }
}
