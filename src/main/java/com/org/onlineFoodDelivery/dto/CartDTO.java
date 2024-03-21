package com.org.onlineFoodDelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private long id;
    @NotNull
    private List<DishDTO> dishes;
    @NotNull
    private UserDTO user;
    @NotNull
    private RestaurantDTO restaurant;
    private double cartValue;
}
