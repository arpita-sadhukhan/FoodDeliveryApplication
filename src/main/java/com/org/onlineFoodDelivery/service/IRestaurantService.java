package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.RestaurantDTO;

import java.util.List;

public interface IRestaurantService {

    public RestaurantDTO addRestaurant(RestaurantDTO dto);
    public RestaurantDTO updateRestaurantStatus(RestaurantDTO dto);

    public List<RestaurantDTO> getAllRestaurants();
    public List<RestaurantDTO> getAllRestaurantsByName(String name);
    public List<RestaurantDTO> getAllRestaurantsByCuisine(String cuisineName);
}
