package com.software.engineering.services;


import com.software.engineering.bo.request.CreateRestaurantRequest;
import com.software.engineering.bo.response.RestaurantResponse;
import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(CreateRestaurantRequest request);

    List<RestaurantResponse> viewRestaurants();

    List<RestaurantResponse> searchRestaurants(String name);
}
