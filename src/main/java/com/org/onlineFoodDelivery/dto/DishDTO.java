package com.org.onlineFoodDelivery.dto;

import lombok.Data;

@Data
public class DishDTO {

    private long id;
    private String name;
    private String description;
    private double price;
    private RestaurantDTO restaurant;
    private CuisineDTO cuisine;
    private boolean available;
}
