package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;

import java.util.List;

public interface IDishesService {

    public List<DishDTO> addDishes(List<DishDTO> dishes);
    public void deleteDish(Long id);

    public List<DishDTO> getDishByRestaurant(Long restaurantId);
    public List<RestaurantDTO> getRestaurantsByDish(String name);
}
