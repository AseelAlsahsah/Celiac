package com.software.engineering.controllers;

import com.software.engineering.bo.request.CreateRestaurantRequest;
import com.software.engineering.bo.response.RestaurantResponse;
import com.software.engineering.services.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        return new ResponseEntity<>(restaurantService.createRestaurant(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> viewRestaurants() {
        return new ResponseEntity<>(restaurantService.viewRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponse>> searchRestaurants(@RequestParam String name) {
        return new ResponseEntity<>(restaurantService.searchRestaurants(name), HttpStatus.OK);
    }
}
